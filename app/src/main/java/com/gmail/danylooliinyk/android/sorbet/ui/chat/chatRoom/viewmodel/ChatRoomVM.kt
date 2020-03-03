package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.viewmodel

import androidx.lifecycle.ViewModel
import com.gmail.danylooliinyk.android.sorbet.data.model.Message

/**
 * ChatRoomVM
 */
abstract class ChatRoomVM : ViewModel() {

    sealed class State {
        data class OnGetMessagesSuccess(val messages: List<Message>) : State()
        data class OnGetMessagesError(val throwable: Throwable) : State()
    }

    abstract fun getMessages(): List<Message>
}
