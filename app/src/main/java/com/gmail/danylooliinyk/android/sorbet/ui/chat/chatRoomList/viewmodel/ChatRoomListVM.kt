package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel

import androidx.lifecycle.ViewModel
import com.gmail.danylooliinyk.android.sorbet.data.model.Message

/**
 * ChatRoomListVM
 */
abstract class ChatRoomListVM : ViewModel() {

    sealed class State {
        data class OnGetMessagesSuccess(val messages: List<Message>) : State()
        data class OnGetMessagesError(val throwable: Throwable) : State()
    }

    abstract fun getChatRooms(): List<Message>
}
