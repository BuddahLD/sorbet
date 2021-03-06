package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.viewmodel

import androidx.lifecycle.*
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.data.model.Message
import com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom.ChatRoomRepository
import com.gmail.danylooliinyk.android.sorbet.data.repository.message.MessagesRepository
import com.gmail.danylooliinyk.android.sorbet.util.SingleLiveEvent
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * ChatRoomVM Default
 */
class ChatRoomVMDefault(
    private val auth: FirebaseAuth,
    private val messagesRepository: MessagesRepository,
    private val chatRoomRepository: ChatRoomRepository
) : ChatRoomVM() {

    private val _liveGetMessages = MediatorLiveData<StateGetMessages>()
    override val liveGetMessages: LiveData<StateGetMessages>
        get() = _liveGetMessages

    private val _liveSendMessage = SingleLiveEvent<StateSendMessage>()
    override val liveSendMessage: LiveData<StateSendMessage>
        get() = _liveSendMessage

    private val _liveGetChatRoom = MediatorLiveData<StateGetChatRoom>()
    override val liveGetChatRoom: LiveData<StateGetChatRoom>
        get() = _liveGetChatRoom

    private val _liveChatRoomDelete = SingleLiveEvent<StateChatRoomDelete>()
    override val liveChatRoomDelete: LiveData<StateChatRoomDelete>
        get() = _liveChatRoomDelete

    override val messageEdit = MutableLiveData<String>()

    override lateinit var currentChatRoom: ChatRoom

    override fun getMessages() {
        val liveData = messagesRepository.getMessages(currentChatRoom.id).asLiveData(
            viewModelScope.coroutineContext + Dispatchers.IO
        )
        _liveGetMessages.addSource(liveData) { value ->
            _liveGetMessages.value = value
        }
    }

    override fun sendMessage(text: String) {
        viewModelScope.launch {
            _liveSendMessage.value = StateSendMessage.OnLoading
            val id = "${UUID.randomUUID()}"
            val message = Message(
                id,
                text,
                Timestamp.now(),
                auth.currentUser!!.uid
            )
            try {
                messagesRepository.sendMessage(message, this@ChatRoomVMDefault.currentChatRoom.id)
            } catch (throwable: Throwable) {
                _liveSendMessage.value = StateSendMessage.OnSendMessageError(throwable)
                return@launch
            }
            _liveSendMessage.value = StateSendMessage.OnMessageSent
        }
    }

    override fun getChatRoom() {
        val liveData = chatRoomRepository.getChatRoom(currentChatRoom.id).asLiveData(
            viewModelScope.coroutineContext + Dispatchers.IO
        )
        _liveGetChatRoom.addSource(liveData) { value ->
            if (value is StateGetChatRoom.OnGetChatRoomSuccess)
                this.currentChatRoom = value.chatRoom

            _liveGetChatRoom.value = value
        }
    }

    override fun deleteChatRoom() {
        viewModelScope.launch {
            _liveChatRoomDelete.value = StateChatRoomDelete.OnLoading
            try {
                chatRoomRepository.deleteChatRoom(currentChatRoom.id)
            } catch (throwable: Throwable) {
                _liveChatRoomDelete.value = StateChatRoomDelete.OnChatRoomDeleteError(throwable)
                return@launch
            }
            _liveChatRoomDelete.value = StateChatRoomDelete.OnChatRoomDeleteSuccess
        }
    }
}
