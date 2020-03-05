package com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom

import com.gmail.danylooliinyk.android.sorbet.api.firestore.FirestoreApi
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel.ChatRoomListVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

/**
 * ChatRoomRepositoryDefault
 */
@Suppress("EXPERIMENTAL_API_USAGE")
class ChatRoomRepositoryDefault(
    private val firestoreApi: FirestoreApi
): ChatRoomRepository {

    override fun getChatRooms(): Flow<ChatRoomListVM.State> =
        firestoreApi.getChatRooms().transform {
            emit(ChatRoomListVM.State.OnLoading)
            val chatRooms = it.toObjects(ChatRoom::class.java)
            emit(ChatRoomListVM.State.OnGetChatRoomsSuccess(chatRooms))
        }

    override fun getChatRoomsDiff(): Flow<ChatRoomListVM.State> =
        firestoreApi.getChatRooms().transform { query ->
            emit(ChatRoomListVM.State.OnLoading)
            val changedDocuments = query.documentChanges.map { documentChange ->
                documentChange.document.toObject(ChatRoom::class.java)
            }
            emit(ChatRoomListVM.State.OnGetChatRoomsSuccess(changedDocuments))
        }

    override fun getChatRoomsPage(size: Int): Flow<ChatRoomListVM.State> {
        throw NotImplementedError("getChatRoomsPage")  // TODO implement
    }
}
