package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.data.model.Message

/**
 * ChatRoomVM
 */
abstract class ChatRoomVM : ViewModel() {

    sealed class StateGetMessages {
        object OnLoading : StateGetMessages()
        data class OnGetMessagesSuccess(val messages: List<Message>) : StateGetMessages()
        data class OnGetMessagesError(val throwable: Throwable) : StateGetMessages()
    }

    sealed class StateSendMessage {
        object OnLoading : StateSendMessage()
        object OnMessageSent : StateSendMessage()
        data class OnSendMessageError(val throwable: Throwable) : StateSendMessage()
    }

    abstract var chatRoom: ChatRoom

    abstract val messageEdit: LiveData<String>
    abstract val liveGetMessages: LiveData<StateGetMessages>
    abstract val liveSendMessage: LiveData<StateSendMessage>

    abstract fun getMessages()

    abstract fun sendMessage(text: String)
}
