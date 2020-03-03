package com.gmail.danylooliinyk.android.sorbet.chat.chatRoomList.viewmodel

import com.gmail.danylooliinyk.android.sorbet.data.model.Message

/**
 * ChatRoomListVM
 */
abstract class ChatRoomListVM {

    sealed class Action {
        data class OnGetMessagesSuccess(val messages: List<Message>) : Action()
        data class OnGetMessagesError(val throwable: Throwable) : Action()
    }

    abstract fun getChatRooms(): List<Message>
}
