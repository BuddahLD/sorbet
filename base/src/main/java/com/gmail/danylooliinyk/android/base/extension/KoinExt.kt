package com.gmail.danylooliinyk.android.base.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import org.koin.androidx.scope.lifecycleScope
import org.koin.androidx.viewmodel.scope.viewModel

/**
 * KoinExt
 */

/**
 * Gets View Model of the Fragment's lifecycleScope. View Model has to be provided as
 * `viewmodel { ... }` in a Koin module.
 */
inline fun <reified T : ViewModel> Fragment.scopeViewModel() =
    this.lifecycleScope.viewModel<T>(this)
