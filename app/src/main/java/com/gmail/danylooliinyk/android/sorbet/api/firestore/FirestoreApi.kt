package com.gmail.danylooliinyk.android.sorbet.api.firestore

import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

/**
 * FirestoreApi
 */
interface FirestoreApi {

    suspend fun anonymousSignIn()

    fun getChatRooms(): Flow<QuerySnapshot>

    suspend fun addRandomChatRoom()

    suspend fun addRandomMessage(chatRoomId: String)

    suspend fun getMessages(chatRoomId: String): Flow<QuerySnapshot>

    // TODO add encryption
}
