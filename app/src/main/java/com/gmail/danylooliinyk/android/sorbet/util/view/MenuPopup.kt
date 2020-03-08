package com.gmail.danylooliinyk.android.sorbet.util.view

import android.content.Context
import android.graphics.Point
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.gmail.danylooliinyk.android.sorbet.R
import com.gmail.danylooliinyk.android.sorbet.util.UiUtils

/**
 * MenuPopup
 */
class MenuPopup : PopupWindow() {

    private lateinit var layout: View
    private lateinit var onClick: (View) -> Unit
    private lateinit var containerView: View

    fun setupPopup(context: Context) {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layout = layoutInflater.inflate(R.layout.layout_popup_menu, null)
        containerView = layout

        contentView = layout
        width = UiUtils.dpToPixels(LAYOUT_PARAMS_WIDTH, context) // TODO rework menu offset for different screens
        height = LAYOUT_PARAMS_HEIGHT
        layout.setPadding(0,
                          UiUtils.dpToPixels(PADDING_TOP, context),
                          0,
                          UiUtils.dpToPixels(PADDING_BOTTOM, context))

        isFocusable = IS_FOCUSABLE
        setBackgroundDrawable(context.getDrawable(R.drawable.rect_gray_rounded_2))
        animationStyle = R.style.popup_window_animation

        val tvMenuItemEdit = containerView.findViewById<TextView>(R.id.tvMenuItemEdit)
        val tvMenuItemLogout = containerView.findViewById<TextView>(R.id.tvMenuItemDelete)

        tvMenuItemEdit.setOnClickListener(onClick)
        tvMenuItemLogout.setOnClickListener(onClick)
    }

    fun showPopup(showPoint: Point) {
        showAtLocation(layout, Gravity.NO_GRAVITY, showPoint.x + OFFSET_X, showPoint.y + OFFSET_Y)
    }

    fun setOnClickListener(onClick: (View) -> Unit) {
        this.onClick = onClick
    }

    companion object {
        const val LAYOUT_PARAMS_WIDTH = 128
        const val LAYOUT_PARAMS_HEIGHT = LinearLayout.LayoutParams.WRAP_CONTENT
        const val PADDING_TOP = 24
        const val PADDING_BOTTOM = 24
        const val IS_FOCUSABLE = true
        const val OFFSET_X = LAYOUT_PARAMS_WIDTH * (-1) - 24
        const val OFFSET_Y = 95
    }
}
