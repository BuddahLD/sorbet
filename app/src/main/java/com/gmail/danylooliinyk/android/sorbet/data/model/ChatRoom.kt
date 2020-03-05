package com.gmail.danylooliinyk.android.sorbet.data.model

import com.google.firebase.firestore.PropertyName

/**
 * ChatRoom
 */
data class ChatRoom(
    @JvmField
    @PropertyName("id")
    val id: String = "",

    @JvmField
    @PropertyName("friendly_name")
    val friendlyName: String = "",

    @JvmField
    @PropertyName("last_message")
    val lastMessage: String = "",

    @JvmField
    @PropertyName("members_count")
    val membersCount: String = "",

    @JvmField
    @PropertyName("picture_path")
    val picturePath: String = ""
)
