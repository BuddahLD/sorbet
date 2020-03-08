package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomEdit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom

/**
 * ChatRoomEditVM
 */
abstract class ChatRoomEditVM : ViewModel() {

    sealed class StateChatRoomEdit {
        object OnLoading : StateChatRoomEdit()
        object OnChatRoomEditSuccess : StateChatRoomEdit()
        data class OnChatRoomDeleteError(val throwable: Throwable) : StateChatRoomEdit()
    }

    sealed class StateChatRoomEditing {
        data class OnChatRoomEdited(val chatRoom: ChatRoom) : StateChatRoomEditing()
        object OnChatRoomNoChanges : StateChatRoomEditing()
    }

    abstract var currentChatRoom: ChatRoom

    abstract val liveChatRoomEdit: LiveData<StateChatRoomEdit>
    abstract val liveChatRoomEditing: LiveData<StateChatRoomEditing>

    abstract fun editChatRoom()
}
