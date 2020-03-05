package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom.ChatRoomRepository
import kotlinx.coroutines.Dispatchers

/**
 * ChatRoomListVM Default
 */
class ChatRoomListVMDefault(
    private val chatRoomRepository: ChatRoomRepository
) : ChatRoomListVM() {

    override fun getChatRooms() = chatRoomRepository.getChatRooms().asLiveData(
        viewModelScope.coroutineContext + Dispatchers.IO
    )

    override fun getChatRoomsDiff() = chatRoomRepository.getChatRoomsDiff().asLiveData(
        viewModelScope.coroutineContext + Dispatchers.IO
    )

    override fun getChatRoomsPage(size: Int) = chatRoomRepository.getChatRoomsPage(size)
        .asLiveData(
            viewModelScope.coroutineContext + Dispatchers.IO
        )
}
