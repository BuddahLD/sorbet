package com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom

import com.gmail.danylooliinyk.android.sorbet.api.firestore.FirestoreApi
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.viewmodel.ChatRoomVM
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel.ChatRoomListVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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
        }.catch {
            emit(ChatRoomListVM.StateGetChatRooms.OnGetChatRoomsError(it))
        }

    override fun getChatRoom(chatRoomId: String): Flow<ChatRoomVM.StateGetChatRoom> =
        firestoreApi.getChatRoom(chatRoomId).transform {
            emit(ChatRoomVM.StateGetChatRoom.OnLoading)
            val chatRoom = it.toObject(ChatRoom::class.java)
                ?: error("ChatRoom with id: \'$chatRoomId\' has not been found.")
            emit(ChatRoomVM.StateGetChatRoom.OnGetChatRoomSuccess(chatRoom))
        }.catch {
            emit(ChatRoomVM.StateGetChatRoom.OnGetChatRoomError(it))
        }

    override fun getChatRoomsDiff(): Flow<ChatRoomListVM.StateGetChatRooms> =
        firestoreApi.getChatRooms().transform { query ->
            emit(ChatRoomListVM.StateGetChatRooms.OnLoading)
            val changedDocuments = query.documentChanges.map { documentChange ->
                documentChange.document.toObject(ChatRoom::class.java)
            }
            emit(ChatRoomListVM.StateGetChatRooms.OnGetChatRoomsSuccess(changedDocuments))
        }.catch {
            emit(ChatRoomListVM.StateGetChatRooms.OnGetChatRoomsError(it))
        }

    // TODO catch errors and transform to live data

    override fun getChatRoomsPage(size: Int): Flow<ChatRoomListVM.StateGetChatRooms> {
        throw NotImplementedError("getChatRoomsPage") // We can fetch paged results if needed
    }

    override suspend fun addRandomChatRoom() = firestoreApi.addRandomChatRoom()

    override suspend fun addChatRoom(chatRoom: ChatRoom) = firestoreApi.addChatRoom(chatRoom)

    override suspend fun deleteChatRoom(chatRoomId: String) =
        firestoreApi.deleteChatRoom(chatRoomId)

    override suspend fun editChatRoom(chatRoomId: String, editedChatRoom: ChatRoom) =
        firestoreApi.editChatRoom(chatRoomId, editedChatRoom)
}
