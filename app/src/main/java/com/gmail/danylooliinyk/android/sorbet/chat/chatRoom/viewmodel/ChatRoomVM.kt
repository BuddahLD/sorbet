package com.gmail.danylooliinyk.android.sorbet.chat.chatRoom.viewmodel

import androidx.lifecycle.ViewModel
import com.gmail.danylooliinyk.android.sorbet.data.model.Message

/**
 * ChatRoomVM
 */
abstract class ChatRoomVM : ViewModel() {

    sealed class Action {
        data class OnGetMessagesSuccess(val messages: List<Message>) : Action()
        data class OnGetMessagesError(val throwable: Throwable) : Action()
    }

    abstract fun getMessages(): List<Message>
}
