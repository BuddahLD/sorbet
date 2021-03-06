package com.gmail.danylooliinyk.android.sorbet.api.firestore

import com.gmail.danylooliinyk.android.sorbet.api.fuel.FuelApi
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.data.model.Message
import com.gmail.danylooliinyk.android.sorbet.util.PictureProvider
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.kiwimob.firestore.coroutines.await
import com.kiwimob.firestore.coroutines.snapshotAsFlow
import kotlinx.coroutines.flow.Flow
import java.util.*
import kotlin.random.Random

/**
 * FirestoreApi
 */
class FirestoreApiDefault(
    private val fuelApi: FuelApi,
    private val pictureProvider: PictureProvider
) : FirestoreApi {

    private val firestore: FirebaseFirestore
    private val auth: FirebaseAuth

    init {
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = settings

        auth = FirebaseAuth.getInstance()
    }

    /**
     * Anonymously Signs In a user, or if user is already signed in - Signs Out, then Signs In.
     */
    override suspend fun anonymousSignIn() {
        if (auth.currentUser == null) {
            auth.signInAnonymously().await()
        } else {
            auth.signOut()
            auth.signInAnonymously().await()
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override fun getChatRooms(): Flow<QuerySnapshot> =
        firestore.collection(CHAT_ROOMS_KEY)
            .orderBy("created_at", Query.Direction.DESCENDING)
            .snapshotAsFlow()

    override fun getChatRoom(chatRoomId: String): Flow<DocumentSnapshot> =
        firestore.collection(CHAT_ROOMS_KEY).document(chatRoomId).snapshotAsFlow()

    /**
     * Adds a [ChatRoom] with randomly generated parameters, then adds a [Message] into the
     * newly created [ChatRoom]. These two operations happen as transaction (batch).
     */
    override suspend fun addRandomChatRoom() {
        val newChatRoom = generateRandomChatRoom()
        val newMessage = generateRandomMessage()

        val addChatRoomRef = getAddChatRoomRef(newChatRoom.id)
        val sendMessageRef = getAddMessageRef(newMessage.id, newChatRoom.id)

        firestore.runBatch { batch ->
            batch.set(addChatRoomRef, newChatRoom)
            batch.set(sendMessageRef, newMessage)
        }.await()
    }

    override suspend fun addChatRoom(chatRoom: ChatRoom) {
        getAddChatRoomRef(chatRoom.id).set(chatRoom).await()
    }

    override fun getMessages(chatRoomId: String): Flow<QuerySnapshot> =
        firestore.collection(CHAT_ROOMS_KEY)
            .document(chatRoomId)
            .collection(MESSAGES_KEY)
            .orderBy("created_at", Query.Direction.ASCENDING)
            .snapshotAsFlow()

    override suspend fun addMessage(message: Message, chatRoomId: String) {
        val addMessageRef = getAddMessageRef(message.id, chatRoomId)
        val chatRoomRef = firestore.collection(CHAT_ROOMS_KEY).document(chatRoomId)

        firestore.runBatch { batch ->
            batch.set(addMessageRef, message)
            batch.update(chatRoomRef, ChatRoom.LAST_MESSAGE_KEY, message.body)
        }.await()
    }

    override suspend fun deleteChatRoom(chatRoomId: String) {
        firestore.collection(CHAT_ROOMS_KEY).document(chatRoomId).delete()
    }

    override suspend fun editChatRoom(chatRoomId: String, editedChatRoom: ChatRoom) {
        val chatRoomRef = firestore.collection(CHAT_ROOMS_KEY).document(chatRoomId)
        firestore.runTransaction {
            val currentChatRoom = it.get(chatRoomRef).toObject(ChatRoom::class.java)
                ?: error("No such chat room with id: \'$chatRoomId\'.")
            val differences = currentChatRoom.getDifference(editedChatRoom)
            it.update(chatRoomRef, differences)
        }.await()
    }

    private suspend fun generateRandomChatRoom(): ChatRoom {
        val id = "${UUID.randomUUID()}"
        val randomGroupName = fuelApi.getRandomGroupName().capitalize()
        val firstTwoLetters = randomGroupName.split(" ")
            .take(2)
            .map { it[0].toUpperCase() }
        val membersCount = Random.nextInt(212, 983).toString()
        val picturePath = pictureProvider.getPicturePath(firstTwoLetters)
        return ChatRoom(
            id,
            randomGroupName,
            Timestamp.now(),
            ENTRY_MESSAGE,
            membersCount,
            picturePath
        )
    }

    private fun generateRandomMessage(): Message {
        val id = "${UUID.randomUUID()}"
        return Message(
            id,
            ENTRY_MESSAGE,
            Timestamp.now(),
            id // Just random sender id for the first message
        )
    }

    private fun getAddChatRoomRef(chatRoomId: String): DocumentReference =
        firestore.collection(CHAT_ROOMS_KEY).document(chatRoomId)

    private fun getAddMessageRef(messageId: String, chatRoomId: String) =
        firestore.collection(CHAT_ROOMS_KEY)
            .document(chatRoomId)
            .collection(MESSAGES_KEY)
            .document(messageId)

    companion object {
        private const val CHAT_ROOMS_KEY = "ChatRooms"
        private const val MESSAGES_KEY = "Messages"

        private const val ENTRY_MESSAGE = "No messages yet (:"
    }
}
