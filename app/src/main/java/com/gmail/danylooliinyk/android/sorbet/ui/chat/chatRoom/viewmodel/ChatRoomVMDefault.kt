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

    private lateinit var chatRoom: ChatRoom

    private val _liveGetMessages = MediatorLiveData<StateGetMessages>()
    override val liveGetMessages: LiveData<StateGetMessages>
        get() = _liveGetMessages

    private val _liveSendMessage = SingleLiveEvent<StateSendMessage>()
    override val liveSendMessage: LiveData<StateSendMessage>
        get() = _liveSendMessage

    private val _liveGetChatRoom = MediatorLiveData<StateGetChatRoom>()
    override val liveGetChatRoom: LiveData<StateGetChatRoom>
        get() = _liveGetChatRoom

    override val messageEdit = MutableLiveData<String>() // TODO try make private

    override fun getMessages() {
        val liveData = messagesRepository.getMessages(chatRoom.id).asLiveData(
            viewModelScope.coroutineContext + Dispatchers.IO
        )
        _liveGetMessages.addSource(liveData) { value ->
            _liveGetMessages.value = value
        } // TODO change setters to kotlin-like
    }

    override fun sendMessage(text: String) {
        viewModelScope.launch {
            // TODO remove Dispatchers.Main in viewModelScope.launch
            _liveSendMessage.value = StateSendMessage.OnLoading
            val id = "${UUID.randomUUID()}"
            val message = Message(
                id,
                text,
                Timestamp.now(),
                auth.currentUser!!.uid
            )
            messagesRepository.sendMessage(message, this@ChatRoomVMDefault.chatRoom.id)
            _liveSendMessage.value = StateSendMessage.OnMessageSent
        }
    }

    override fun getChatRoom(chatRoomId: String) {
        val liveData = chatRoomRepository.getChatRoom(chatRoomId).asLiveData(
            viewModelScope.coroutineContext + Dispatchers.IO
        )
        _liveGetChatRoom.addSource(liveData) { value ->
            if (value is StateGetChatRoom.OnGetChatRoomSuccess)
                this.chatRoom = value.chatRoom

            _liveGetChatRoom.value = value
        }
    }
}
