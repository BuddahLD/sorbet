package com.gmail.danylooliinyk.android.sorbet.data.repository.message

import com.gmail.danylooliinyk.android.sorbet.data.model.Message
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.viewmodel.ChatRoomVM
import kotlinx.coroutines.flow.Flow

/**
 * MessagesRepository
 */
interface MessagesRepository {

    /**
     * Returns all messages list. On snapshot changes returns whole list as well.
     */
    fun getMessages(chatRoomId: String): Flow<ChatRoomVM.StateGetMessages>

    /**
     * Returns only difference of a snapshot changes.
     */
    fun getMessagesDiff(chatRoomId: String): Flow<ChatRoomVM.StateGetMessages>

    /**
     * Returns messages page with a provided [size].
     */
    fun getMessagesPage(size: Int): Flow<ChatRoomVM.StateGetMessages>

    /**
     * Adds a [Message] to the firestore with provided [message].
     */
    suspend fun sendMessage(message: Message, chatRoomId: String)
}
