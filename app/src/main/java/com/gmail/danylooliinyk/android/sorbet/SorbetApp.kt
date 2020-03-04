package com.gmail.danylooliinyk.android.sorbet

import android.app.Application
import com.google.firebase.FirebaseApp

/**
 * SorbetApp
 */
class SorbetApp : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}
