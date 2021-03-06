package com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom

import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.viewmodel.ChatRoomVM
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel.ChatRoomListVM
import kotlinx.coroutines.flow.Flow

/**
 * ChatRoomRepository
 */
interface ChatRoomRepository {

    /**
     * Returns all chat rooms list. On snapshot changes returns whole list as well.
     */
    fun getChatRooms(): Flow<ChatRoomListVM.StateGetChatRooms>

    /**
     * Returns [ChatRoom] snapshot and further changes.
     */
    fun getChatRoom(chatRoomId: String): Flow<ChatRoomVM.StateGetChatRoom>

    /**
     * Returns only difference of a snapshot changes.
     */
    fun getChatRoomsDiff(): Flow<ChatRoomListVM.StateGetChatRooms>

    /**
     * Returns chat rooms page with a provided [size].
     */
    fun getChatRoomsPage(size: Int): Flow<ChatRoomListVM.StateGetChatRooms>

    /**
     * Adds a [ChatRoom] to the firestore with randomly generated parameters.
     */
    suspend fun addRandomChatRoom()

    /**
     * Adds a [ChatRoom] to the firestore with provided [chatRoom].
     */
    suspend fun addChatRoom(chatRoom: ChatRoom)

    /**
     * Deletes a [ChatRoom] with a provided [chatRoomId].
     */
    suspend fun deleteChatRoom(chatRoomId: String)

    /**
     * Edits a [ChatRoom] with a provided [chatRoomId] with given [editedChatRoom].
     */
    suspend fun editChatRoom(chatRoomId: String, editedChatRoom: ChatRoom)
}
