package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomEdit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom.ChatRoomRepository
import com.gmail.danylooliinyk.android.sorbet.util.SingleLiveEvent

/**
 * ChatRoomEditVM
 */
class ChatRoomEditVMDefault(
    private val chatRoomRepository: ChatRoomRepository
) : ChatRoomEditVM() {

    private val chatRoomNameObserver: (String) -> Unit

    private val _liveChatRoomEdit = SingleLiveEvent<StateChatRoomEdit>()
    override val liveChatRoomEdit: LiveData<StateChatRoomEdit>
        get() = _liveChatRoomEdit

    private val _liveChatRoomEditing = SingleLiveEvent<StateChatRoomEditing>()
    override val liveChatRoomEditing: LiveData<StateChatRoomEditing>
        get() = _liveChatRoomEditing

    val chatRoomNameEdit = MutableLiveData<String>()

    override lateinit var currentChatRoom: ChatRoom

    init {
        this.chatRoomNameObserver = { newName ->
            if (newName != currentChatRoom.friendlyName) {
                val updatedChatRoom = currentChatRoom.copy(friendlyName = newName)
                this.currentChatRoom = updatedChatRoom

                _liveChatRoomEditing.value =
                    StateChatRoomEditing.OnChatRoomEdited(updatedChatRoom)
            } else {
                _liveChatRoomEditing.value = StateChatRoomEditing.OnChatRoomNoChanges
            }
        }
        chatRoomNameEdit.observeForever(chatRoomNameObserver)
    }

    override fun editChatRoom(editedChatRoom: ChatRoom) {
        // TODO Implement body or it will be empty ):
    }

    override fun onCleared() {
        super.onCleared()

        chatRoomNameEdit.removeObserver(chatRoomNameObserver)
    }
}
