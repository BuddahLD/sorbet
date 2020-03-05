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
    private val chatRoomRepository: ChatRoomRepository
) : ChatRoomListVM() {

    override fun getChatRooms() {
        val liveData = chatRoomRepository.getChatRooms().asLiveData(
            viewModelScope.coroutineContext + Dispatchers.IO
        )
        state.addSource(liveData) { value -> state.setValue(value) }
    }

    override fun getChatRoomsDiff() {
        val liveData = chatRoomRepository.getChatRoomsDiff().asLiveData(
            viewModelScope.coroutineContext + Dispatchers.IO
        )
        state.addSource(liveData) { value -> state.setValue(value) }
    }

    override fun getChatRoomsPage(size: Int) {
        val liveData = chatRoomRepository.getChatRoomsPage(size)
            .asLiveData(
                viewModelScope.coroutineContext + Dispatchers.IO
            )
        state.addSource(liveData) { value -> state.setValue(value) }
    }

    override fun addRandomChatRoom() {
        val liveData = chatRoomRepository.addRandomChatRoom()
            .asLiveData(
                viewModelScope.coroutineContext + Dispatchers.IO
            )
        state.addSource(liveData) { value -> state.setValue(value) }
    }

    override fun getState(): LiveData<State> = state
}
