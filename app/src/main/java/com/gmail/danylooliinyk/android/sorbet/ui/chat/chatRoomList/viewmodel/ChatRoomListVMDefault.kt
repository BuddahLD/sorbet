package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel

import androidx.lifecycle.*
import com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom.ChatRoomRepository
import com.gmail.danylooliinyk.android.sorbet.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ChatRoomListVM Default
 */
class ChatRoomListVMDefault(
    private val repository: ChatRoomRepository
) : ChatRoomListVM() {

    private val _liveAddChatRoom = SingleLiveEvent<StateAddChatRoom>()
    override val liveAddChatRoom: LiveData<StateAddChatRoom>
        get() = _liveAddChatRoom

    private val _liveGetChatRoom = MediatorLiveData<StateGetChatRooms>()
    override val liveGetChatRoom: LiveData<StateGetChatRooms>
        get() = _liveGetChatRoom

    override fun getChatRooms() {
        val liveData = repository.getChatRooms().asLiveData(
            viewModelScope.coroutineContext + Dispatchers.IO
        )
        _liveGetChatRoom.addSource(liveData) { value -> _liveGetChatRoom.setValue(value) }
    }

    override fun addRandomChatRoom() {
        viewModelScope.launch(Dispatchers.IO) {
            _liveAddChatRoom.value = StateAddChatRoom.OnLoading
            repository.addRandomChatRoom()
            _liveAddChatRoom.value = StateAddChatRoom.OnChatRoomAdded
        }
    }
}
