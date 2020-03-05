package com.gmail.danylooliinyk.android.sorbet.util

import com.gmail.danylooliinyk.android.sorbet.data.model.User

/**
 * Preferences
 */
interface Preferences {

    fun setUser(user: User)

    fun getUser(): User
}
