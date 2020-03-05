package com.gmail.danylooliinyk.android.base.viewmodel

import androidx.lifecycle.LiveData

/**
 * StatefulViewModel
 */
interface StatefulViewModel<T> {

    fun getState(): LiveData<T>
}
