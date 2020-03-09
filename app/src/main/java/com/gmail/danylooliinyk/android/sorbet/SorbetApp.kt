package com.gmail.danylooliinyk.android.sorbet

import android.app.Application
import com.gmail.danylooliinyk.android.sorbet.di.*
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

/**
 * SorbetApp
 */
class SorbetApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            printLogger()

            androidContext(this@SorbetApp)

            modules(
                // Common
                commonModule,

                // Firebase
                firebaseModule,

                // Chat room
                chatRoomModule,

                // Chat room edit
                chatRoomEditModule,

                // Chat room list
                chatRoomListModule,

                // Sign In
                signInModule,

                // Sign Out
                signOutModule
            )
        }

        FirebaseApp.initializeApp(this)
    }
}
