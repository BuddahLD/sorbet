package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.viewmodel

import androidx.lifecycle.ViewModel
import com.gmail.danylooliinyk.android.base.viewmodel.StatefulViewModel
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.data.model.Message

/**
 * ChatRoomVM
 */
abstract class ChatRoomVM : ViewModel(), StatefulViewModel<ChatRoomVM.State> {

    sealed class State {
        object OnLoading : State()
        data class OnGetMessagesSuccess(val messages: List<Message>) : State()
        data class OnGetMessagesError(val throwable: Throwable) : State()
        object OnMessageSent : State()
    }

    abstract var chatRoom: ChatRoom

    abstract fun getMessages()

    abstract fun sendMessage(text: String)
}
