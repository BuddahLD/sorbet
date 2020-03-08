package com.gmail.danylooliinyk.android.sorbet.util

import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import com.google.android.material.snackbar.Snackbar

/**
 * UiUtils
 */
object UiUtils {

    fun showSnackbar(view: View, text: String, duration: Int = Snackbar.LENGTH_SHORT) =
        Snackbar.make(view, text, duration).show()

    /**
     * This method converts density independent pixels (dp) to equivalent pixels,
     * depending on device density.
     * Where [dp] is a dp value which we need to convert into pixels.
     */
    fun dpToPixels(dp: Int, context: Context): Int =
        context.resources.displayMetrics.let {
            dp * (it.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }.toInt()

    /**
     * This method converts device specific pixels (dp) to density independent pixels.
     * Where [px] is a value in pixels, which we need to convert into dp.
     */
    fun pixelsToDp(px: Int, context: Context): Int =
        context.resources.displayMetrics.let {
            px / (it.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }.toInt()

    fun getStatusBarHeight(window: Window): Int {
        val rectangle = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rectangle)
        return rectangle.top
    }
}
