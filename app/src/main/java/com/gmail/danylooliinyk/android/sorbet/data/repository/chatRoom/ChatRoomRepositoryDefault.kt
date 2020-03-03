package com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom

import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom

/**
 * ChatRoomRepositoryDefault
 */
class ChatRoomRepositoryDefault: ChatRoomRepository {

    override fun getChatRooms(): List<ChatRoom> {
        throw NotImplementedError()
    }
}
