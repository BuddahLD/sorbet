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
) : ChatRoomRepository {

    override fun getChatRooms(): Flow<ChatRoomListVM.StateGetChatRooms> =
        firestoreApi.getChatRooms().transform {
            emit(ChatRoomListVM.StateGetChatRooms.OnLoading)
            val chatRooms = it.toObjects(ChatRoom::class.java)
            emit(ChatRoomListVM.StateGetChatRooms.OnGetChatRoomsSuccess(chatRooms))
        }

    override fun getChatRoomsDiff(): Flow<ChatRoomListVM.StateGetChatRooms> =
        firestoreApi.getChatRooms().transform { query ->
            emit(ChatRoomListVM.StateGetChatRooms.OnLoading)
            val changedDocuments = query.documentChanges.map { documentChange ->
                documentChange.document.toObject(ChatRoom::class.java)
            }
            emit(ChatRoomListVM.StateGetChatRooms.OnGetChatRoomsSuccess(changedDocuments))
        } // TODO catch errors and transform to live data

    override fun getChatRoomsPage(size: Int): Flow<ChatRoomListVM.StateGetChatRooms> {
        throw NotImplementedError("getChatRoomsPage") // We can fetch paged results if needed
    }

    override suspend fun addRandomChatRoom() = firestoreApi.addRandomChatRoom()

    override suspend fun addChatRoom(chatRoom: ChatRoom) = firestoreApi.addChatRoom(chatRoom)
}
