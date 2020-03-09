package com.gmail.danylooliinyk.android.sorbet.data.repository.message

import com.gmail.danylooliinyk.android.sorbet.api.firestore.FirestoreApi
import com.gmail.danylooliinyk.android.sorbet.data.model.Message
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.viewmodel.ChatRoomVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.transform

/**
 * MessagesRepositoryDefault
 */
class MessagesRepositoryDefault(
    private val firestoreApi: FirestoreApi
): MessagesRepository {

    override fun getMessages(chatRoomId: String): Flow<ChatRoomVM.StateGetMessages> =
        firestoreApi.getMessages(chatRoomId).transform {
        emit(ChatRoomVM.StateGetMessages.OnLoading)
        val chatRooms = it.toObjects(Message::class.java)
        emit(ChatRoomVM.StateGetMessages.OnGetMessagesSuccess(chatRooms))
    }.catch { emit(ChatRoomVM.StateGetMessages.OnGetMessagesError(it)) }

    override fun getMessagesDiff(chatRoomId: String): Flow<ChatRoomVM.StateGetMessages> =
        firestoreApi.getMessages(chatRoomId).transform { query ->
            emit(ChatRoomVM.StateGetMessages.OnLoading)
            val changedDocuments = query.documentChanges.map { documentChange ->
                documentChange.document.toObject(Message::class.java)
            }
            emit(ChatRoomVM.StateGetMessages.OnGetMessagesSuccess(changedDocuments))
        }.catch { emit(ChatRoomVM.StateGetMessages.OnGetMessagesError(it)) }

    override fun getMessagesPage(size: Int): Flow<ChatRoomVM.StateGetMessages> {
        throw NotImplementedError("getMessagesPage")
    }

    override suspend fun sendMessage(message: Message, chatRoomId: String) =
        firestoreApi.addMessage(message, chatRoomId)
}
