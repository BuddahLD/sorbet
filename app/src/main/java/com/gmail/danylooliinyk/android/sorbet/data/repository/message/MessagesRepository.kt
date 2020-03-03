package com.gmail.danylooliinyk.android.sorbet.data.repository.message

import com.gmail.danylooliinyk.android.sorbet.data.model.Message

/**
 * MessagesRepository
 */
interface MessagesRepository {

    fun getMessages(): List<Message>
}
