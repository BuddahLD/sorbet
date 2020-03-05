package com.gmail.danylooliinyk.android.sorbet.api.firestore

import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel.ChatRoomListVM
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

/**
 * FirestoreApi
 */
interface FirestoreApi {

    suspend fun anonymousSignIn()

    fun getChatRooms(): Flow<QuerySnapshot>

    suspend fun addRandomChatRoom()

    suspend fun addChatRoom(chatRoom: ChatRoom)

    suspend fun addRandomMessage(chatRoomId: String)

    suspend fun getMessages(chatRoomId: String): Flow<QuerySnapshot>

    // TODO add encryption
}
