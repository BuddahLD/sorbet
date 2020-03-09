package com.gmail.danylooliinyk.android.sorbet.ui.signOut

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import com.gmail.danylooliinyk.android.base.extension.observe
import com.gmail.danylooliinyk.android.base.extension.scopeViewModel
import com.gmail.danylooliinyk.android.base.view.fragment.BaseFragment
import com.gmail.danylooliinyk.android.sorbet.R
import com.gmail.danylooliinyk.android.sorbet.ui.signOut.viewmodel.SignOutVM
import com.gmail.danylooliinyk.android.sorbet.util.UiUtils

/**
 * ChatRoomListFragment
 */
class SignOutFragment : BaseFragment(R.layout.fragment_sing_out) {

    private val vm: SignOutVM by scopeViewModel()

    private lateinit var pbLoading: View

    override fun initObjects(context: Context) {}

    override fun initObservers() {
        observe(vm.liveSignOut, ::onStateChanged)
    }

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        with(view) {
            val btnSignOut: View = findViewById(R.id.btnSignOut)
            btnSignOut.setOnClickListener {
                vm.signOut()
            }
            this@SignOutFragment.pbLoading = findViewById(R.id.pbLoading)
        }
    }

    override fun initData() {}

    private fun onStateChanged(state: SignOutVM.State): Unit = when (state) {
        is SignOutVM.State.OnLoading -> showLoading(pbLoading, true)
        is SignOutVM.State.OnSignOutSuccess -> {
            showLoading(pbLoading, false)
            val action = SignOutFragmentDirections.actionSignOutFragmentToSignInFragment()
            findNavController().navigate(action)
        }
        is SignOutVM.State.OnSignOutError -> {
            showLoading(pbLoading, false)
            UiUtils.showSnackbar(
                requireView(),
                state.throwable.localizedMessage
                    ?: "Unhandled error. Contact developer, please."
            )
        }
    }
}
