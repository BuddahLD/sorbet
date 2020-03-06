package com.gmail.danylooliinyk.android.sorbet.data.repository.message

import com.gmail.danylooliinyk.android.sorbet.api.firestore.FirestoreApi
import com.gmail.danylooliinyk.android.sorbet.data.model.Message
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.viewmodel.ChatRoomVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transform

/**
 * MessagesRepositoryDefault
 */
class MessagesRepositoryDefault(
    private val firestoreApi: FirestoreApi
): MessagesRepository {

    override fun getMessages(chatRoomId: String): Flow<ChatRoomVM.State> =
        firestoreApi.getMessages(chatRoomId).transform {
        emit(ChatRoomVM.State.OnLoading)
        val chatRooms = it.toObjects(Message::class.java)
        emit(ChatRoomVM.State.OnGetMessagesSuccess(chatRooms))
    }

    override fun getMessagesDiff(chatRoomId: String): Flow<ChatRoomVM.State> =
        firestoreApi.getMessages(chatRoomId).transform { query ->
            emit(ChatRoomVM.State.OnLoading)
            val changedDocuments = query.documentChanges.map { documentChange ->
                documentChange.document.toObject(Message::class.java)
            }
            emit(ChatRoomVM.State.OnGetMessagesSuccess(changedDocuments))
        }

    override fun getMessagesPage(size: Int): Flow<ChatRoomVM.State> {
        throw NotImplementedError("getMessagesPage")
    }

    override fun sendMessage(message: Message, chatRoomId: String): Flow<ChatRoomVM.State> = flow {
        emit(ChatRoomVM.State.OnLoading)
        firestoreApi.addMessage(message, chatRoomId)
        emit(ChatRoomVM.State.OnMessageSent)
    }


}
