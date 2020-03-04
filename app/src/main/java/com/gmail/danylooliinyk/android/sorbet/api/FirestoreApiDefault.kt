package com.gmail.danylooliinyk.android.sorbet.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.QuerySnapshot
import com.kiwimob.firestore.coroutines.snapshotAsFlow
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.suspendCoroutine

/**
 * FirestoreApi
 */
class FirestoreApiDefault : FirestoreApi {

    private val firestore: FirebaseFirestore
    private val auth: FirebaseAuth

    init {
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        firestore = FirebaseFirestore.getInstance()
        firestore.firestoreSettings = settings

        auth = FirebaseAuth.getInstance()
    }

    /**
     * Anonymously Signs In a user, or if user is already signed in - Signs Out, then Signs In.
     */
    override suspend fun anonymousSignIn() = suspendCoroutine<Unit> {
        if (auth.currentUser == null) {
            auth.signInAnonymously().result
        } else {
            auth.signOut()
            auth.signInAnonymously().result
        }
    }

    override fun getChatRooms(): Flow<QuerySnapshot> = firestore.collection(CHAT_ROOMS_KEY).snapshotAsFlow()

//    val result = it.toObjects(ChatRoom::class.java)

    companion object {
        private val CHAT_ROOMS_KEY = "ChatRooms"
    }
}
