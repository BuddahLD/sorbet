package com.gmail.danylooliinyk.android.sorbet.api.firestore

import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.data.model.Message
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

    fun getMessages(chatRoomId: String): Flow<QuerySnapshot>

    suspend fun addMessage(message: Message, chatRoomId: String)

    // TODO add encryption
}
