package com.example.jiun.ssok.server

import android.support.v7.widget.RecyclerView
import com.example.jiun.ssok.model.DualModel
import com.example.jiun.ssok.util.ViewHolderFactory
import java.io.Serializable

data class RecordResponse(
        var id: Int = 0,
        var db_id:Int =0,
        var view: Int = 0,
        var category: String = "",
        var division: String = "",
        var url: String = "",
        var date: String = "",
        var title: String = "",
        var content: String = "",
        var attach: String = ""
) : Serializable, DualModel {

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder) {
        val webHolder = viewHolder as ViewHolderFactory.DualHolder
        webHolder.categoryTextView.text = "웹-$category-$division"
        webHolder.titleTextVIew.text = title
        webHolder.dateTextView.text = date
    }

    override fun getItemViewType(): Int {
        return DualModel.RECORD_RESPONSE
    }

    override fun toString(): String {
        return title
    }
}