
@file:JvmName("DualModel")

package com.example.jiun.sookpam.model

import android.support.v7.widget.RecyclerView
import android.widget.ImageView

interface DualModel {
    companion object {
        const val RECORD_VO: Int = 1
        const val RECORD_RESPONSE: Int = 2
    }

    fun getItemViewType(): Int

    fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder)

    override fun toString():String

    fun setStarIcon(view : ImageView, title : String)
}
