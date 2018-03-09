package com.example.jiun.sookpam.web.common

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.server.RecordResponse
import com.example.jiun.sookpam.web.WebRecordReformation
import kotlinx.android.synthetic.main.web_common_recycler_item.view.*

class WebCommonRecyclerAdapter(private val records: List<RecordResponse>?) : RecyclerView.Adapter<WebCommonRecyclerAdapter.ViewHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        context = parent!!.context
        val view = LayoutInflater.from(context).inflate(R.layout.web_common_recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return records!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val record = records!![position]
        holder!!.titleTextView.text = WebRecordReformation.getTitleSubstring(record.title)
        holder.dateTextView.text = record.date
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var titleTextView = view.web_common_item_title_txt!!
        var dateTextView = view.web_common_item_date_txt!!
    }
}