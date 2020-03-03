package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.viewmodel

import androidx.lifecycle.LiveData
import com.gmail.danylooliinyk.android.sorbet.data.model.Message
import com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom.ChatRoomRepository

/**
 * ChatRoomVM Default
 */
class ChatRoomVMDefault(
    private val state: LiveData<State>,
    private val repository: ChatRoomRepository
) : ChatRoomVM() {

    override fun getMessages(): List<Message> {
        throw NotImplementedError("getMessages()")
    }
}
