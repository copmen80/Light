package com.electro.light.utils

import androidx.recyclerview.widget.DiffUtil

internal class DefaultDiffCallback<T : Any>(
        private val areContentsTheSameCallback: (oldItem: T, newItem: T) -> Boolean
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem::class.java.simpleName == newItem::class.java.simpleName
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return areContentsTheSameCallback(oldItem, newItem)
    }
}
