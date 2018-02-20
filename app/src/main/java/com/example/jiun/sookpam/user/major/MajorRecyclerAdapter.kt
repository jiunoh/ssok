package com.example.jiun.sookpam.user.major

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jiun.sookpam.R
import kotlinx.android.synthetic.main.major_recycler_item.view.*

class MajorRecyclerAdapter(val data: List<String>) : RecyclerView.Adapter<MajorRecyclerAdapter.ViewHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        context = parent!!.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.major_recycler_item, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = data[position]
        holder!!.majorTextView.text = item
        holder.itemView.setBackgroundColor(Color.WHITE)
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var majorTextView = v.major_txt!!
    }
}
