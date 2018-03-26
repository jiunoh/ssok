package com.example.jiun.ssok.web.common

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jiun.ssok.R
import com.example.jiun.ssok.server.RecordResponse
import com.example.jiun.ssok.web.WebRecordReformation
import kotlinx.android.synthetic.main.web_career_recycler_item.view.*

class WebCareerRecyclerAdapter(private val records: List<RecordResponse>?) : RecyclerView.Adapter<WebCareerRecyclerAdapter.ViewHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        context = parent!!.context
        val view = LayoutInflater.from(context).inflate(R.layout.web_career_recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return records?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val record = records!![position]
        holder!!.titleTextView.text = WebRecordReformation.getTitleSubstring(record.title, record.category, record.division)
        holder.dateTextView.text = record.date
        holder.divisionTextView.text = record.division
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var titleTextView = view.web_career_item_title_txt!!
        var dateTextView = view.web_career_item_date_txt!!
        var divisionTextView = view.web_career_item_division_txt!!
    }
}