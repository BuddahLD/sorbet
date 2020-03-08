package com.gmail.danylooliinyk.android.sorbet.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName
import kotlinx.android.parcel.Parcelize

/**
 * ChatRoom
 */
@Parcelize
data class ChatRoom(
    @JvmField
    @PropertyName(ID_KEY)
    val id: String = "",

    @JvmField
    @PropertyName(FRIENDLY_NAME_KEY)
    val friendlyName: String = "",

    @JvmField
    @PropertyName(CREATED_AT_KEY)
    val createdAt: Timestamp = Timestamp.now(),

    @JvmField
    @PropertyName(LAST_MESSAGE_KEY)
    val lastMessage: String = "",

    @JvmField
    @PropertyName(MEMBERS_COUNT_KEY)
    val membersCount: String = "",

    @JvmField
    @PropertyName(PICTURE_PATH_KEY)
    val picturePath: String = ""
): Comparable<ChatRoom>, Parcelable {

    fun getDifference(other: ChatRoom): Map<String, Any> {
        val differences = mutableMapOf<String, Any>()

        if (id != other.id) differences[ID_KEY] = other.id
        if (friendlyName != other.friendlyName) differences[FRIENDLY_NAME_KEY] = other.friendlyName
        if (createdAt != other.createdAt) differences[CREATED_AT_KEY] = other.createdAt
        if (lastMessage != other.lastMessage) differences[LAST_MESSAGE_KEY] = other.lastMessage
        if (membersCount != other.membersCount) differences[MEMBERS_COUNT_KEY] = other.membersCount
        if (picturePath != other.picturePath) differences[PICTURE_PATH_KEY] = other.picturePath

        return differences.toMap()
    }

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

    companion object {
        const val ID_KEY = "id"
        const val FRIENDLY_NAME_KEY = "friendly_name"
        const val CREATED_AT_KEY = "created_at"
        const val LAST_MESSAGE_KEY = "last_message"
        const val MEMBERS_COUNT_KEY = "members_count"
        const val PICTURE_PATH_KEY = "picture_path"
    }
}
