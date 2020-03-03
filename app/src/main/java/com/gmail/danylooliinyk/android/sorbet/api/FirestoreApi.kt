package com.gmail.danylooliinyk.android.sorbet.api

import android.util.Log
import com.gmail.danylooliinyk.android.sorbet.data.model.ChatRoom
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.kiwimob.firestore.coroutines.snapshotAsFlow
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.suspendCoroutine

/**
 * FirestoreApi
 */
class FirestoreApi {

    /**
     * Anonymously Signs In a user, or if user is already signed in - Signs Out, then Signs In.
     */
    suspend fun anonymousSignIn() = suspendCoroutine<Unit> {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            auth.signInAnonymously().result
        } else {
            auth.signOut()
            auth.signInAnonymously().result
        }
    }

    fun getChatRooms(): Flow<QuerySnapshot> =
            FirebaseFirestore
                .getInstance()
                .collection(CHAT_ROOMS_KEY)
                .snapshotAsFlow()

//    val result = it.toObjects(ChatRoom::class.java)

    companion object {
        private val CHAT_ROOMS_KEY = "ChatRooms"
    }
}
