package com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom

import androidx.lifecycle.LiveData
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel.ChatRoomListVM
import kotlinx.coroutines.flow.Flow
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom

/**
 * ChatRoomRepository
 */
interface ChatRoomRepository {

    /**
     * Should return all chat rooms list. On snapshot changes returns whole list as well.
     */
    fun getChatRooms(): Flow<ChatRoomListVM.State>

    /**
     * Returns only difference of a snapshot changes.
     */
    fun getChatRoomsDiff(): Flow<ChatRoomListVM.State>

    /**
     * Returns chat rooms page with a provided [size].
     */
    fun getChatRoomsPage(size: Int): Flow<ChatRoomListVM.State>

    /**
     * Adds a [ChatRoom] to the firestore with randomly generated parameters.
     */
    fun addRandomChatRoom(): Flow<ChatRoomListVM.State>

    /**
     * Adds a [ChatRoom] to the firestore with provided [chatRoom].
     */
    fun addChatRoom(chatRoom: ChatRoom): Flow<ChatRoomListVM.State>
}
