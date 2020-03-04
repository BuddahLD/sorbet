package com.gmail.danylooliinyk.android.sorbet.api

import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

/**
 * FirestoreApi
 */
interface FirestoreApi {

    suspend fun anonymousSignIn()

    fun getChatRooms(): Flow<QuerySnapshot>
}
