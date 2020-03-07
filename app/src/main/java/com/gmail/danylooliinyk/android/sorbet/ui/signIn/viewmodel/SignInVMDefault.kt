package com.gmail.danylooliinyk.android.sorbet.ui.signIn.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.gmail.danylooliinyk.android.sorbet.api.firestore.FirestoreApi
import com.gmail.danylooliinyk.android.sorbet.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ChatRoomListVM Default
 */
class SignInVMDefault(
    private val firestoreApi: FirestoreApi
) : SignInVM() {

    private val _liveAnonymousSignIn = SingleLiveEvent<State>()
    override val liveAnonymousSignIn: LiveData<State>
        get() = _liveAnonymousSignIn

    override fun anonymousSignIn() {
        viewModelScope.launch(Dispatchers.Main) {
            _liveAnonymousSignIn.value = State.OnLoading
            firestoreApi.anonymousSignIn()
            _liveAnonymousSignIn.value = State.OnSignInSuccess
        }
    }
}
