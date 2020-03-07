package com.gmail.danylooliinyk.android.sorbet.ui.signIn

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import com.gmail.danylooliinyk.android.base.extension.observe
import com.gmail.danylooliinyk.android.base.extension.scopeViewModel
import com.gmail.danylooliinyk.android.base.view.fragment.BaseFragment
import com.gmail.danylooliinyk.android.sorbet.R
import com.gmail.danylooliinyk.android.sorbet.ui.signIn.viewmodel.SignInVM
import com.gmail.danylooliinyk.android.sorbet.util.UiUtils
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject

/**
 * ChatRoomListFragment
 */
class SignInFragment : BaseFragment(R.layout.fragment_sign_in) {

    private val auth: FirebaseAuth by inject()
    private val vm: SignInVM by scopeViewModel()

    private lateinit var pbLoading: ProgressBar

    override fun initObjects(context: Context) {
        if (auth.currentUser != null) navigateToChatRoomList() // TODO change transition animation if user logged in to avoid blink
    }

    override fun initObservers() {
        observe(vm.liveAnonymousSignIn, ::onStateChanged)
    }

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        with(view) {
            val btnSignIn: View = findViewById(R.id.btnSignIn)
            btnSignIn.setOnClickListener {
                vm.anonymousSignIn()
            }
            this@SignInFragment.pbLoading = findViewById(R.id.pbLoading)
        }
    }

    override fun initData() {}

    private fun onStateChanged(state: SignInVM.State): Unit = when (state) {
        is SignInVM.State.OnLoading -> showLoading(pbLoading, true)
        is SignInVM.State.OnSignInSuccess -> {
            showLoading(pbLoading, false)
            navigateToChatRoomList()
        }
        is SignInVM.State.OnSignInError -> { // TODO check where error is been thrown
            showLoading(pbLoading, false)
            UiUtils.showSnackbar(
                requireView(),
                state.throwable.localizedMessage
                    ?: "Unhandled error. Contact developer, please."
            )
        }
    }

    private fun navigateToChatRoomList() {
        val action = SignInFragmentDirections.actionSignInFragmentToChatRoomListFragment()
        findNavController().navigate(action)
    }
}
