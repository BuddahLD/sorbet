package com.gmail.danylooliinyk.android.sorbet.data.repository.message

import com.gmail.danylooliinyk.android.sorbet.api.FirestoreApi
import com.gmail.danylooliinyk.android.sorbet.data.model.Message

/**
 * MessagesRepositoryDefault
 */
class MessagesRepositoryDefault(
    private val firestoreApi: FirestoreApi
): MessagesRepository {

    override fun getMessages(): List<Message> {
        throw NotImplementedError()
    }
}
