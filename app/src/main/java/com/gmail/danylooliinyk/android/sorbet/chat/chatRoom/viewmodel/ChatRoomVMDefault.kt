package com.gmail.danylooliinyk.android.sorbet.chat.chatRoom.viewmodel

import com.gmail.danylooliinyk.android.sorbet.data.model.Message

/**
 * ChatRoomVM
 */
class ChatRoomVMDefault : ChatRoomVM() {

    override fun getMessages(): List<Message> {
        throw NotImplementedError("getMessages()")
    }
}
