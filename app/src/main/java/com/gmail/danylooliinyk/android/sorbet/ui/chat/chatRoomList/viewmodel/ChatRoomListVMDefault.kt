package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom.ChatRoomRepository
import kotlinx.coroutines.Dispatchers

/**
 * ChatRoomListVM Default
 */
class ChatRoomListVMDefault(
    private val state: MediatorLiveData<State>,
    private val repository: ChatRoomRepository
) : ChatRoomListVM() {

    override fun getChatRooms() {
        val liveData = repository.getChatRooms().asLiveData(
            viewModelScope.coroutineContext + Dispatchers.IO
        )
        state.addSource(liveData) { value -> state.setValue(value) }
    }

    override fun getChatRoomsDiff() {
        val liveData = repository.getChatRoomsDiff().asLiveData(
            viewModelScope.coroutineContext + Dispatchers.IO
        )
        state.addSource(liveData) { value -> state.setValue(value) }
    }

    override fun getChatRoomsPage(size: Int) {
        val liveData = repository.getChatRoomsPage(size)
            .asLiveData(
                viewModelScope.coroutineContext + Dispatchers.IO
            )
        state.addSource(liveData) { value -> state.setValue(value) }
    }

    override fun addRandomChatRoom() {
        val liveData = repository.addRandomChatRoom()
            .asLiveData(
                viewModelScope.coroutineContext + Dispatchers.IO
            )
        state.addSource(liveData) { value -> state.setValue(value) }
    }

    override fun getState(): LiveData<State> = state
}
