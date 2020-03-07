package com.gmail.danylooliinyk.android.base.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * Base Activity with LifecycleRegistry and ViewModel setup function.
 */
abstract class BaseFragmentBinding(
    @LayoutRes private val contentLayoutId: Int
) : BaseFragment() {

    /**
     * Used to initialize view binding
     */
    protected abstract fun initViewBinding(inflater: LayoutInflater,
                                           container: ViewGroup?,
                                           @LayoutRes layoutResourceId: Int): View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initViewBinding(inflater, container, contentLayoutId)
    }
}
