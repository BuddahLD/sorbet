package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomEdit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom.ChatRoomRepository
import com.gmail.danylooliinyk.android.sorbet.util.SingleLiveEvent
import kotlinx.coroutines.launch

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
    private lateinit var updatedChatRoom: ChatRoom

    init {
        this.chatRoomNameObserver = { newName ->
            if (newName != currentChatRoom.friendlyName) {
                val updatedChatRoom = currentChatRoom.copy(friendlyName = newName)
                this.updatedChatRoom = updatedChatRoom

                _liveChatRoomEditing.value =
                    StateChatRoomEditing.OnChatRoomEdited(updatedChatRoom)
            } else {
                _liveChatRoomEditing.value = StateChatRoomEditing.OnChatRoomNoChanges
            }
        }
        chatRoomNameEdit.observeForever(chatRoomNameObserver)
    }

    override fun editChatRoom() {
        viewModelScope.launch {
            _liveChatRoomEdit.value = StateChatRoomEdit.OnLoading
            this@ChatRoomEditVMDefault.currentChatRoom = updatedChatRoom
            chatRoomRepository.editChatRoom(currentChatRoom.id, updatedChatRoom)
            _liveChatRoomEdit.value = StateChatRoomEdit.OnChatRoomEditSuccess
        }
    }

    override fun onCleared() {
        super.onCleared()

        chatRoomNameEdit.removeObserver(chatRoomNameObserver)
    }
}
