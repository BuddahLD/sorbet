package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom

/**
 * ChatRoomListVM
 */
abstract class ChatRoomListVM : ViewModel(){

    sealed class State {
        object OnLoading : State()
        data class OnGetChatRoomsSuccess(val chatRooms: List<ChatRoom>) : State()
        data class OnGetChatRoomsError(val throwable: Throwable) : State()
    }

    abstract fun getChatRooms(): LiveData<State>

    abstract fun getChatRoomsDiff(): LiveData<State>

    abstract fun getChatRoomsPage(size: Int): LiveData<State>
}
