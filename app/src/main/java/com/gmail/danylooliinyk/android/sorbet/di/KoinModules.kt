package com.gmail.danylooliinyk.android.sorbet.di

import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.ChatRoomListFragment
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel.ChatRoomListVM
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel.ChatRoomListVMDefault
import com.gmail.danylooliinyk.android.sorbet.util.moduleWithScope
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * KoinModules
 */
val commonModule: Module = module {

}

val chatRoomModule: Module = module {

}

val chatRoomListModule: Module = moduleWithScope(named<ChatRoomListFragment>()) {
    viewModel { ChatRoomListVMDefault() as ChatRoomListVM }
}
