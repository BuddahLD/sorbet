package com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.data.model.Message
import com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom.ChatRoomRepository
import com.gmail.danylooliinyk.android.sorbet.data.repository.message.MessagesRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import java.util.*

/**
 * ChatRoomVM Default
 */
class ChatRoomVMDefault(
    private val state: MediatorLiveData<State>,
    private val auth: FirebaseAuth,
    private val repository: MessagesRepository
) : ChatRoomVM() {

    override lateinit var chatRoom: ChatRoom

    override fun getMessages() {
        val liveData = repository.getMessages(chatRoom.id).asLiveData(
            viewModelScope.coroutineContext + Dispatchers.IO
        )
        state.addSource(liveData) { value -> state.setValue(value) }
    }

    override fun sendMessage(text: String) {
        val id = "${UUID.randomUUID()}"
        val message = Message(
            id,
            text,
            Timestamp.now(),
            auth.currentUser!!.uid
        )
        val liveData = repository.sendMessage(message, this.chatRoom.id).asLiveData(
            viewModelScope.coroutineContext + Dispatchers.IO
        )
        state.addSource(liveData) { value -> state.setValue(value) }
    }


    override fun getState(): LiveData<State> = state
}
