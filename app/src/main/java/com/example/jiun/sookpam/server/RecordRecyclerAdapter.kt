package com.example.jiun.sookpam.server

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jiun.sookpam.R
import kotlinx.android.synthetic.main.record_recycler_item.view.*

class RecordRecyclerAdapter(private val records: List<RecordResponse>?) : RecyclerView.Adapter<RecordRecyclerAdapter.ViewHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        context = parent!!.context
        val view = LayoutInflater.from(context).inflate(R.layout.record_recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return records!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val record = records!![position]
        holder!!.titleTextView.text = record.title
        holder.dateTextView.text = record.date
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var titleTextView = view.record_item_title_txt!!
        var dateTextView = view.record_item_date_txt!!
    }
}