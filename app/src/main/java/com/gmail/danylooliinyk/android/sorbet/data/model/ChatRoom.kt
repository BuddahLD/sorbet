package com.gmail.danylooliinyk.android.sorbet.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom.Companion.TABLE_NAME

/**
 * ChatRoom
 */
@Entity(tableName = TABLE_NAME)
data class ChatRoom(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID_KEY)
    val id: String,

    @ColumnInfo(name = LAST_MESSAGE_KEY)
    val lastMessage: String,

    @ColumnInfo(name = MEMBERS_COUNT_KEY)
    val membersCount: Int
) {
    companion object {
        const val TABLE_NAME = "chat_room_table"

        const val ID_KEY = "id"
        const val LAST_MESSAGE_KEY = "last_message"
        const val MEMBERS_COUNT_KEY = "members_count"
    }
}
