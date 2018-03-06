package com.example.jiun.sookpam.server

import android.support.v7.widget.RecyclerView
import android.widget.ViewSwitcher
import com.example.jiun.sookpam.searchable.SearchItem
import com.example.jiun.sookpam.util.ViewHolderFactory
import java.io.Serializable

data class RecordResponse(
        var id: Int = 0,
        var view: Int = 0,
        var category: String = "",
        var division: String = "",
        var url: String = "",
        var date: String = "",
        var title: String = "",
        var content: String = ""
) : Serializable, SearchItem {
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder) {
        val webHolder = viewHolder as ViewHolderFactory.WebHolder
        webHolder.categoryTextView.text = category
        webHolder.titleTextVIew.text = title
    }

    override fun getItemViewType(): Int {
        return SearchItem.RECORD_RESPONSE
    }
}
