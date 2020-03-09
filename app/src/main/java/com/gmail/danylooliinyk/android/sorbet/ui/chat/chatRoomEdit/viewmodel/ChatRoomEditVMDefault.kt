package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomEdit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom.ChatRoomRepository
import com.gmail.danylooliinyk.android.sorbet.util.PictureProvider
import com.gmail.danylooliinyk.android.sorbet.util.SingleLiveEvent
import kotlinx.coroutines.launch

/**
 * ChatRoomEditVM
 */
class ChatRoomEditVMDefault(
    private val chatRoomRepository: ChatRoomRepository,
    private val pictureProvider: PictureProvider
) : ChatRoomEditVM() {

    private val chatRoomNameObserver: (String) -> Unit

    private val _liveChatRoomEditName = SingleLiveEvent<StateChatRoomEditName>()
    override val liveChatRoomEditName: LiveData<StateChatRoomEditName>
        get() = _liveChatRoomEditName

    private val _liveChatRoomEditing = SingleLiveEvent<StateChatRoomEditing>()
    override val liveChatRoomEditing: LiveData<StateChatRoomEditing>
        get() = _liveChatRoomEditing

    private val _liveChatRoomEditPicture = SingleLiveEvent<StateChatRoomEditPicture>()
    override val liveChatRoomEditPicture: LiveData<StateChatRoomEditPicture>
        get() = _liveChatRoomEditPicture

    val chatRoomNameEdit = MutableLiveData<String>()

    override lateinit var currentChatRoom: ChatRoom
    private lateinit var updatedChatRoom: ChatRoom

    init {
        this.chatRoomNameObserver = { newName ->
            val newNameTrimmed = newName.trim()
            if (newNameTrimmed != currentChatRoom.friendlyName) {
                val updatedChatRoom = currentChatRoom.copy(friendlyName = newNameTrimmed)
                this.updatedChatRoom = updatedChatRoom

                _liveChatRoomEditing.value =
                    StateChatRoomEditing.OnChatRoomEdited(updatedChatRoom)
            } else {
                _liveChatRoomEditing.value = StateChatRoomEditing.OnChatRoomNoChanges
            }
        }
        chatRoomNameEdit.observeForever(chatRoomNameObserver)
    }

    override fun editChatRoomName() {
        viewModelScope.launch {
            _liveChatRoomEditName.value = StateChatRoomEditName.OnLoading
            this@ChatRoomEditVMDefault.currentChatRoom = updatedChatRoom

            try {
                chatRoomRepository.editChatRoom(currentChatRoom.id, updatedChatRoom)
            } catch (throwable: Throwable) {
                _liveChatRoomEditName.value =
                    StateChatRoomEditName.OnChatRoomEditNameError(throwable)
                return@launch
            }
            _liveChatRoomEditName.value = StateChatRoomEditName.OnChatRoomEditNameSuccess
        }
    }

    override fun editChatRoomPicture() {
        viewModelScope.launch {
            _liveChatRoomEditPicture.value = StateChatRoomEditPicture.OnLoading
            val firstTwoLetters = currentChatRoom.friendlyName.split(" ")
                .take(2)
                .map { it[0].toUpperCase() }
            val picturePath = pictureProvider.getPicturePath(firstTwoLetters)
            this@ChatRoomEditVMDefault.currentChatRoom =
                currentChatRoom.copy(picturePath = picturePath)

            try {
                chatRoomRepository.editChatRoom(currentChatRoom.id, currentChatRoom)
            } catch (throwable: Throwable) {
                _liveChatRoomEditPicture.value =
                    StateChatRoomEditPicture.OnChatRoomEditPictureError(throwable)
                return@launch
            }
            _liveChatRoomEditPicture.value =
                StateChatRoomEditPicture.OnChatRoomEditPictureSuccess(currentChatRoom.picturePath)
        }
    }

    override fun onCleared() {
        super.onCleared()

        chatRoomNameEdit.removeObserver(chatRoomNameObserver)
    }
}
