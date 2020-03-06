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
    fun getMessages(chatRoomId: String): Flow<ChatRoomVM.State>

    /**
     * Returns only difference of a snapshot changes.
     */
    fun getMessagesDiff(chatRoomId: String): Flow<ChatRoomVM.State>

    /**
     * Returns messages page with a provided [size].
     */
    fun getMessagesPage(size: Int): Flow<ChatRoomVM.State>

    /**
     * Adds a [Message] to the firestore with provided [message].
     */
    fun sendMessage(message: Message, chatRoomId: String): Flow<ChatRoomVM.State>
}
