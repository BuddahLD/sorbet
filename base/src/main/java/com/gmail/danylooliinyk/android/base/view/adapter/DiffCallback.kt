package com.gmail.danylooliinyk.android.base.view.adapter

import androidx.recyclerview.widget.DiffUtil

open class DiffCallback<T : Comparable<T>> : DiffUtil.Callback() {

    private var oldList: List<T> = emptyList()
    private var newList: List<T> = emptyList()

    fun setLists(oldList: List<T>, newList: List<T>) {
        this.oldList = oldList
        this.newList = newList
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].compareTo(newList[newItemPosition]) == 0

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            (oldList[oldItemPosition] == newList[newItemPosition])
}
