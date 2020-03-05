package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gmail.danylooliinyk.android.base.viewmodel.StatefulViewModel
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom

/**
 * ChatRoomListVM
 */
abstract class ChatRoomListVM : ViewModel(), StatefulViewModel<ChatRoomListVM.State> {

    sealed class State {
        object OnLoading : State()
        data class OnGetChatRoomsSuccess(val chatRooms: List<ChatRoom>) : State()
        data class OnGetChatRoomsError(val throwable: Throwable) : State()
        object OnChatRoomAdded : State()
    }

    abstract fun getChatRooms()

    abstract fun getChatRoomsDiff()

    abstract fun getChatRoomsPage(size: Int)

    abstract fun addRandomChatRoom()
}
