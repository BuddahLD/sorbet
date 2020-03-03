package com.gmail.danylooliinyk.android.sorbet.dao.chatRoom

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom

/**
 * ChatRoomDao
 */
interface ChatRoomDao {

    @Query("SELECT * FROM chat_room_table")
    suspend fun getChatRooms(): List<ChatRoom>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertChatRooms(messages: List<ChatRoom>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertChatRoom(message: ChatRoom)

    @Query("SELECT COUNT(*) from chat_room_table")
    suspend fun chatRoomsCount(): Int
}
