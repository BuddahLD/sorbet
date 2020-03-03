package com.gmail.danylooliinyk.android.sorbet.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.danylooliinyk.android.sorbet.data.model.Message.Companion.TABLE_NAME
import java.util.*

/**
 * Message
 */
@Entity(tableName = TABLE_NAME)
data class Message(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_KEY)
    val id: String,

    @ColumnInfo(name = THREAD_ID_KEY)
    val threadId: String,

    @ColumnInfo(name = BODY_KEY)
    val body: String,

    @ColumnInfo(name = CREATED_AT_KEY)
    val createdAt: Date,

    @ColumnInfo(name = SENDER_KEY)
    val sender: String
) {
    companion object {
        const val TABLE_NAME = "message_table"

        const val ID_KEY = "id"
        const val THREAD_ID_KEY = "thread_id"
        const val BODY_KEY = "body"
        const val CREATED_AT_KEY = "created_at"
        const val SENDER_KEY = "sender"
    }
}
