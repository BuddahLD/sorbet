package com.gmail.danylooliinyk.android.sorbet.ui.signOut.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

/**
 * ChatRoomListVM
 */
abstract class SignOutVM : ViewModel(){

    sealed class State {
        object OnLoading : State()
        object OnSignOutSuccess : State()
        data class OnSignOutError(val throwable: Throwable) : State()
    }

    abstract val liveSignOut: LiveData<State>

    abstract fun signOut()
}
