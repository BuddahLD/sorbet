package com.gmail.danylooliinyk.android.base.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 * Base Activity with LifecycleRegistry and ViewModel setup function.
 */
abstract class BaseFragment(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId) {

    /**
     * Used to initialize general objects.
     */
    protected abstract fun initObjects(context: Context)

    /**
     * Used to initialize observers.
     */
    protected abstract fun initObservers()

    /**
     * Used to initialize views.
     */
    protected abstract fun initViews(view: View, savedInstanceState: Bundle?)

    /**
     * Used to initialize data.
     */
    protected abstract fun initData()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        initObjects(context)
        initObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        initData()
    }

    protected fun hideKeyboard() {
        val imm =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }
}
