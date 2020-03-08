package com.gmail.danylooliinyk.android.sorbet.di

import com.gmail.danylooliinyk.android.sorbet.api.firestore.FirestoreApi
import com.gmail.danylooliinyk.android.sorbet.api.firestore.FirestoreApiDefault
import com.gmail.danylooliinyk.android.sorbet.api.fuel.FuelApi
import com.gmail.danylooliinyk.android.sorbet.api.fuel.FuelApiDefault
import com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom.ChatRoomRepository
import com.gmail.danylooliinyk.android.sorbet.data.repository.chatRoom.ChatRoomRepositoryDefault
import com.gmail.danylooliinyk.android.sorbet.data.repository.message.MessagesRepository
import com.gmail.danylooliinyk.android.sorbet.data.repository.message.MessagesRepositoryDefault
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.ChatRoomFragment
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.viewmodel.ChatRoomVM
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoom.viewmodel.ChatRoomVMDefault
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomEdit.ChatRoomEditFragment
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomEdit.viewmodel.ChatRoomEditVM
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomEdit.viewmodel.ChatRoomEditVMDefault
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.ChatRoomListFragment
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel.ChatRoomListVM
import com.gmail.danylooliinyk.android.sorbet.ui.chat.chatRoomList.viewmodel.ChatRoomListVMDefault
import com.gmail.danylooliinyk.android.sorbet.ui.signIn.SignInFragment
import com.gmail.danylooliinyk.android.sorbet.ui.signIn.viewmodel.SignInVM
import com.gmail.danylooliinyk.android.sorbet.ui.signIn.viewmodel.SignInVMDefault
import com.gmail.danylooliinyk.android.sorbet.ui.signOut.SignOutFragment
import com.gmail.danylooliinyk.android.sorbet.ui.signOut.viewmodel.SignOutVM
import com.gmail.danylooliinyk.android.sorbet.ui.signOut.viewmodel.SignOutVMDefault
import com.gmail.danylooliinyk.android.sorbet.util.PictureProvider
import com.gmail.danylooliinyk.android.sorbet.util.PictureProviderUiAvatars
import com.gmail.danylooliinyk.android.sorbet.util.ResourcesUtil
import com.gmail.danylooliinyk.android.sorbet.util.moduleWithScope
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * KoinModules
 */
val firebaseModule: Module = module {
    single { FuelApiDefault() as FuelApi }
    single { FirebaseAuth.getInstance() }
    single { FirestoreApiDefault(get(), get()) as FirestoreApi }
}

val commonModule: Module = module {
    single { ResourcesUtil(get()) }
    single { PictureProviderUiAvatars(get()) as PictureProvider }

    single { ChatRoomRepositoryDefault(get()) as ChatRoomRepository }
    single { MessagesRepositoryDefault(get()) as MessagesRepository }
}

val chatRoomModule: Module = moduleWithScope(named<ChatRoomFragment>()) {
    viewModel { ChatRoomVMDefault(get(), get(), get()) as ChatRoomVM }
}

val chatRoomListModule: Module = moduleWithScope(named<ChatRoomListFragment>()) {
    viewModel { ChatRoomListVMDefault(get()) as ChatRoomListVM }
}

val signInModule: Module = moduleWithScope(named<SignInFragment>()) {
    viewModel { SignInVMDefault(get()) as SignInVM }
}

val signOutModule: Module = moduleWithScope(named<SignOutFragment>()) {
    viewModel { SignOutVMDefault(get()) as SignOutVM }
}

val chatRoomEditModule: Module = moduleWithScope(named<ChatRoomEditFragment>()) {
    viewModel { ChatRoomEditVMDefault(get()) as ChatRoomEditVM }
}
