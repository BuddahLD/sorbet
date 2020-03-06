package com.gmail.danylooliinyk.android.base.view.adapter

import androidx.annotation.LayoutRes
import android.view.View
import android.view.ViewGroup
import com.gmail.danylooliinyk.android.base.extension.inflate

interface DelegateAdapterItem<VH : BaseViewHolder<T>, T> where T : Comparable<T> {

    @get:LayoutRes
    val layoutResourceId: Int

    fun createViewHolder(parent: View): VH

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> =
            parent.inflate(layoutResourceId, false)
                    .let { createViewHolder(it) }
                    .apply {
                        setOnInflatedListener { objectType, view ->
                            onViewHolderInflated(view, objectType, this)
                        }
                    }

    fun onBindViewHolder(holder: VH, items: List<T>, position: Int) = holder.onInflated(items[position])

    fun onViewHolderInflated(view: View, item: T, viewHolder: VH)

    fun onRecycled(holder: VH)

    fun isForViewType(items: List<*>, position: Int): Boolean
}
