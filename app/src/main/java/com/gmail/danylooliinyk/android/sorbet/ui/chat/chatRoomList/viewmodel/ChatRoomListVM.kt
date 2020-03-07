package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom

/**
 * ChatRoomListVM
 */
abstract class ChatRoomListVM : ViewModel() {

    sealed class StateGetChatRooms {
        object OnLoading : StateGetChatRooms()
        data class OnGetChatRoomsSuccess(val chatRooms: List<ChatRoom>) : StateGetChatRooms()
        data class OnGetChatRoomsError(val throwable: Throwable) : StateGetChatRooms()
    }

    sealed class StateAddChatRoom {
        object OnLoading : StateAddChatRoom()
        object OnChatRoomAdded : StateAddChatRoom()
        data class OnGetChatRoomsError(val throwable: Throwable) : StateAddChatRoom()
    }

    abstract val liveGetChatRoom: LiveData<StateGetChatRooms>
    abstract val liveAddChatRoom: LiveData<StateAddChatRoom>

    abstract fun getChatRooms()

    abstract fun addRandomChatRoom()
}
