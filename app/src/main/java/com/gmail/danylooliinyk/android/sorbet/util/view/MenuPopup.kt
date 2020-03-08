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

    fun setupPopup(context: Context) {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layout = layoutInflater.inflate(R.layout.layout_popup_menu, null)
        contentView = layout

        width = UiUtils.dpToPixels(MENU_WIDTH, context)
        height = LinearLayout.LayoutParams.WRAP_CONTENT

        isFocusable = true
        animationStyle = R.style.popup_window_animation

        val tvMenuItemEdit = layout.findViewById<TextView>(R.id.tvMenuItemEdit)
        val tvMenuItemLogout = layout.findViewById<TextView>(R.id.tvMenuItemDelete)

        tvMenuItemEdit.setOnClickListener(onClick)
        tvMenuItemLogout.setOnClickListener(onClick)
    }

    fun showPopup(showPoint: Point) {
        showAtLocation(layout, Gravity.NO_GRAVITY, showPoint.x, showPoint.y)
    }

    fun setOnClickListener(onClick: (View) -> Unit) {
        this.onClick = onClick
    }

    companion object {
        const val MENU_WIDTH = 128
    }
}
