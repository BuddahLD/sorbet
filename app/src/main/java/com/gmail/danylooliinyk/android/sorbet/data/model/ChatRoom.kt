package com.gmail.danylooliinyk.android.sorbet.data.model

import android.os.Parcelable
import com.gmail.danylooliinyk.android.sorbet.api.firestore.FirestoreApiDefault
import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

/**
 * ChatRoom
 */
@Parcelize
data class ChatRoom(
    @JvmField
    @PropertyName("id")
    val id: String = "",

    @JvmField
    @PropertyName("friendly_name")
    val friendlyName: String = "",

    @JvmField
    @PropertyName("created_at")
    val createdAt: Timestamp = Timestamp.now(),

    @JvmField
    @PropertyName(FirestoreApiDefault.LAST_MESSAGE_KEY)
    val lastMessage: String = "",

    @JvmField
    @PropertyName("members_count")
    val membersCount: String = "",

    @JvmField
    @PropertyName("picture_path")
    val picturePath: String = ""
): Comparable<ChatRoom>, Parcelable {

    override fun compareTo(other: ChatRoom): Int = this.id.compareTo(other.id)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChatRoom

        if (id != other.id) return false
        if (friendlyName != other.friendlyName) return false
        if (createdAt != other.createdAt) return false
        if (lastMessage != other.lastMessage) return false
        if (membersCount != other.membersCount) return false
        if (picturePath != other.picturePath) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + friendlyName.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + lastMessage.hashCode()
        result = 31 * result + membersCount.hashCode()
        result = 31 * result + picturePath.hashCode()
        return result
    }
}
