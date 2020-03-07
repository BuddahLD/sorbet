package com.gmail.danylooliinyk.android.sorbet.ui.signIn.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

/**
 * ChatRoomListVM
 */
abstract class SignInVM : ViewModel() {

    sealed class State {
        object OnLoading : State()
        object OnSignInSuccess : State()
        data class OnSignInError(val throwable: Throwable) : State()
    }

    abstract val liveAnonymousSignIn: LiveData<State>

    abstract fun anonymousSignIn()
}
