package com.gmail.danylooliinyk.android.sorbet.api.firestore

import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.data.model.Message
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

/**
 * FirestoreApi
 */
interface FirestoreApi {

    suspend fun anonymousSignIn()

    suspend fun signOut()

    fun getChatRooms(): Flow<QuerySnapshot>

    fun getChatRoom(chatRoomId: String): Flow<DocumentSnapshot>

    suspend fun addRandomChatRoom()

    suspend fun addChatRoom(chatRoom: ChatRoom)

    fun getMessages(chatRoomId: String): Flow<QuerySnapshot>

    suspend fun addMessage(message: Message, chatRoomId: String)

    // TODO add encryption
}
