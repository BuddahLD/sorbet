package com.gmail.danylooliinyk.android.base.view.activity

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 * Base Activity with LifecycleRegistry and ViewModel setup function.
 */
abstract class BaseActivity(
    @LayoutRes contentLayoutId: Int
) : AppCompatActivity(contentLayoutId) {

    /**
     * Used to initialize general options.
     */
    protected abstract fun init(savedInstanceState: Bundle?)

    /**
     * Used to initialize View Model State Observers.
     */
    protected abstract fun setupVMStateObservers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init(savedInstanceState)
        setupVMStateObservers()
    }

    protected fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
    }
}
