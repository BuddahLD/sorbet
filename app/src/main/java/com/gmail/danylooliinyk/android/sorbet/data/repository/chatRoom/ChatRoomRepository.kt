package com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom

import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom

/**
 * ChatRoomRepository
 */
interface ChatRoomRepository {

    fun getChatRooms(): List<ChatRoom>
}
