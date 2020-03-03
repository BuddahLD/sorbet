package com.gmail.danylooliinyk.android.sorbet.dao.message

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gmail.danylooliinyk.android.sorbet.data.model.Message

/**
 * MessageDao
 */
interface MessageDao {

    @Query("SELECT * FROM message_table WHERE thread_id LIKE (:threadId)")
    suspend fun getMessages(threadId: String): List<Message>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessages(messages: List<Message>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessage(message: Message)

    @Query("SELECT COUNT(*) from message_table WHERE thread_id LIKE (:threadId)")
    suspend fun messagesCount(threadId: String): Int
}
