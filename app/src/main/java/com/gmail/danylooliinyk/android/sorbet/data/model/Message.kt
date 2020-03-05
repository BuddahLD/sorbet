package com.gmail.danylooliinyk.android.sorbet.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Message
 */
@Parcelize
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
): Comparable<Message>, Parcelable {

    override fun compareTo(other: Message): Int = this.id.compareTo(other.id)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Message

        if (id != other.id) return false
        if (body != other.body) return false
        if (createdAt != other.createdAt) return false
        if (sender != other.sender) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + body.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + sender.hashCode()
        return result
    }
}
