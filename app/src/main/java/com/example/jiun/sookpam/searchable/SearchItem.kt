@file:JvmName("SearchItem")

package com.example.jiun.sookpam.searchable

import android.support.v7.widget.RecyclerView

interface SearchItem {
    companion object {
        const val RECORD_VO: Int = 1
        const val RECORD_RESPONSE: Int = 2
    }

    fun getItemViewType(): Int

    fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder)
}
