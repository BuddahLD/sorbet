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
        viewModelScope.launch {
            _liveAnonymousSignIn.value = State.OnLoading
            try {
                firestoreApi.anonymousSignIn()
            } catch (throwable: Throwable) {
                _liveAnonymousSignIn.value = State.OnSignInError(throwable)
                return@launch
            }
            _liveAnonymousSignIn.value = State.OnSignInSuccess
        }
    }
}
