package com.gmail.danylooliinyk.android.base.view.adapter

import android.content.Context
import android.content.res.Resources
import android.view.View

abstract class DelegateAdapterItemDefault<T>
    : DelegateAdapterItem<DelegateAdapterItemDefault.KViewHolder<T>, T> where T : Comparable<T> {

    open fun onCreated(view: View) = Unit

    abstract fun onBind(item: T, viewHolder: KViewHolder<T>)

    final override fun onViewHolderInflated(view: View, item: T, viewHolder: KViewHolder<T>) {
        onBind(item, viewHolder)
    }

    override fun createViewHolder(parent: View): KViewHolder<T> {
        return KViewHolder(parent, ::onCreated)
    }

    class KViewHolder<T>(val containerView: View, onCreated: (View) -> Unit)
        : BaseViewHolder<T>(containerView) where T : Comparable<T> {

        val context: Context = containerView.context
        val resources: Resources = containerView.resources

        init {
            onCreated(containerView)
        }
    }
}
