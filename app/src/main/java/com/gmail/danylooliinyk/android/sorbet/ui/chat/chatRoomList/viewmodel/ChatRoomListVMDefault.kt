package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel

import com.gmail.danylooliinyk.android.sorbet.data.model.Message

/**
 * ChatRoomListVM Default
 */
class ChatRoomListVMDefault: ChatRoomListVM() {

    override fun getChatRooms(): List<Message> {
        throw NotImplementedError()
    }
}
