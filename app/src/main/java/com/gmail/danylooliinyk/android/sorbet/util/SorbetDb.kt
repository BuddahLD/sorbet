package com.gmail.danylooliinyk.android.sorbet.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gmail.danylooliinyk.android.sorbet.dao.chatRoom.ChatRoomDao
import com.gmail.danylooliinyk.android.sorbet.dao.message.MessageDao
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.gmail.danylooliinyk.android.sorbet.data.model.Message

/**
 * SorbetDb
 */
@Database(entities = [Message::class, ChatRoom::class], version = 1, exportSchema = false)
abstract class SorbetDb(context: Context) : RoomDatabase() {

    init {
        Room.databaseBuilder(
            context.applicationContext,
            SorbetDb::class.java,
            DB_NAME
        ).build()
    }

    abstract fun messageDao(): MessageDao

    abstract fun chatRoomDao(): ChatRoomDao

    companion object {
        private const val DB_NAME = "sorbet_database"
    }
}
