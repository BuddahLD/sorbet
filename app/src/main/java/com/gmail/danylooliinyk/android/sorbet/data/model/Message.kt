package com.gmail.danylooliinyk.android.sorbet.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName
import java.util.*

/**
 * Message
 */
data class Message(
    @JvmField
    @PropertyName("body")
    val id: String,

    @JvmField
    @PropertyName("body")
    val body: String,

    @JvmField
    @PropertyName("created_at")
    val createdAt: Timestamp,

    @JvmField
    @PropertyName("sender")
    val sender: String
)
