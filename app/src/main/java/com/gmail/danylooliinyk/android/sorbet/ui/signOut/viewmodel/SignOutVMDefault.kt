package com.gmail.danylooliinyk.android.sorbet.ui.signOut.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gmail.danylooliinyk.android.sorbet.api.firestore.FirestoreApi
import com.gmail.danylooliinyk.android.sorbet.util.SingleLiveEvent
import kotlinx.coroutines.launch

/**
 * ChatRoomListVM Default
 */
class SignOutVMDefault(
    private val firestoreApi: FirestoreApi
) : SignOutVM() {

    private val _liveSignOut = SingleLiveEvent<State>()
    override val liveSignOut: LiveData<State>
        get() = _liveSignOut

    override fun signOut() {
        viewModelScope.launch {
            _liveSignOut.value = State.OnLoading
            firestoreApi.signOut()
            _liveSignOut.value = State.OnSignOutSuccess
        }
    }
}
