package com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom

import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel.ChatRoomListVM
import kotlinx.coroutines.flow.Flow

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
}
