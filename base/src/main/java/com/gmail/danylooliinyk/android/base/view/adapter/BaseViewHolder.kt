package com.gmail.danylooliinyk.android.base.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(containerView: View)
    : RecyclerView.ViewHolder(containerView) where T : Comparable<T> {

    private var listener: ((viewType: T, view: View) -> Unit)? = null

    fun setOnInflatedListener(listener: (viewType: T, view: View) -> Unit) {
        this.listener = listener
    }

    fun onInflated(item: T) {
        listener?.invoke(item, itemView)
    }
}
