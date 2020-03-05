package com.gmail.danylooliinyk.android.sorbet

import android.app.Application
import com.gmail.danylooliinyk.android.sorbet.di.chatRoomListModule
import com.gmail.danylooliinyk.android.sorbet.di.chatRoomModule
import com.gmail.danylooliinyk.android.sorbet.di.commonModule
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

                // Chat room
                chatRoomModule,

                // Chat room list
                chatRoomListModule
            )
        }

        FirebaseApp.initializeApp(this)
    }
}
