package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomEdit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom

/**
 * ChatRoomEditVM
 */
abstract class ChatRoomEditVM : ViewModel() {

    sealed class StateChatRoomEditName {
        object OnLoading : StateChatRoomEditName()
        object OnChatRoomEditNameSuccess : StateChatRoomEditName()
        data class OnChatRoomEditNameError(val throwable: Throwable) : StateChatRoomEditName()
    }

    sealed class StateChatRoomEditPicture {
        object OnLoading : StateChatRoomEditPicture()
        data class OnChatRoomEditPictureSuccess(val picPath: String) : StateChatRoomEditPicture()
        data class OnChatRoomEditPictureError(val throwable: Throwable) : StateChatRoomEditPicture()
    }

    sealed class StateChatRoomEditing {
        data class OnChatRoomEdited(val chatRoom: ChatRoom) : StateChatRoomEditing()
        object OnChatRoomNoChanges : StateChatRoomEditing()
    }

    abstract var currentChatRoom: ChatRoom

    abstract val liveChatRoomEditName: LiveData<StateChatRoomEditName>
    abstract val liveChatRoomEditPicture: LiveData<StateChatRoomEditPicture>
    abstract val liveChatRoomEditing: LiveData<StateChatRoomEditing>

    abstract fun editChatRoomName()

    abstract fun editChatRoomPicture()
}
