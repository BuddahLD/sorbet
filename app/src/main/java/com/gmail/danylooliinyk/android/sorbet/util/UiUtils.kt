package com.gmail.danylooliinyk.android.sorbet.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 * UiUtils
 */
object UiUtils {

    fun showSnackbar(view: View, text: String, duration: Int = Snackbar.LENGTH_SHORT) =
        Snackbar.make(view, text, duration).show()
}
