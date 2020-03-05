package com.gmail.danylooliinyk.android.sorbet.api.firestore

import com.gmail.danylooliinyk.android.sorbet.api.fuel.FuelApi
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.data.model.Message
import com.gmail.danylooliinyk.android.sorbet.util.PictureProvider
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.QuerySnapshot
import com.kiwimob.firestore.coroutines.await
import com.kiwimob.firestore.coroutines.snapshotAsFlow
import kotlinx.coroutines.flow.Flow
import java.util.*

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

    override fun getChatRooms(): Flow<QuerySnapshot> =
        firestore.collection(CHAT_ROOMS_KEY).snapshotAsFlow()

    override suspend fun addRandomChatRoom() {
        val collection = firestore.collection(CHAT_ROOMS_KEY)
        val id = "${UUID.randomUUID()}"
        val randomGroupName = fuelApi.getRandomGroupName()
        val firstTwoLetters = randomGroupName.split(" ")
            .take(2)
            .map { it[0].toUpperCase() }
        val chatRoom = ChatRoom(
            id,
            randomGroupName,
            ENTRY_MESSAGE,
            "666",
            pictureProvider.getPicturePath(firstTwoLetters)
        )
        collection.document(id).set(chatRoom).await()
    }

    override suspend fun addRandomMessage(chatRoomId: String) {
        val collection = firestore.collection(CHAT_ROOMS_KEY)
            .document(chatRoomId)
            .collection(MESSAGES_KEY)

        val id = "${UUID.randomUUID()}"
        val chatRoom = Message(
            id,
            "Text of message is very interesting",
            Timestamp(Date()),
            "Some sender"
        )
        collection.document(id).set(chatRoom).await()
    }

    override suspend fun getMessages(chatRoomId: String): Flow<QuerySnapshot> =
        firestore.collection(CHAT_ROOMS_KEY)
            .document(chatRoomId)
            .collection(MESSAGES_KEY)
            .snapshotAsFlow()

    companion object {
        private val CHAT_ROOMS_KEY = "ChatRooms"
        private val MESSAGES_KEY = "Messages"

        private val ENTRY_MESSAGE = "No messages yet (:"
    }
}
