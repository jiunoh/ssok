package com.example.jiun.sookpam.user.major

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.major_recycler_item.view.*

class MajorRecyclerAdapter(val data: List<String>) : RecyclerView.Adapter<MajorRecyclerAdapter.ViewHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        context = parent!!.context
        val view = LayoutInflater.from(context).inflate(R.layout.major_recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = data[position]
        holder!!.majorTextView.text = item
        holder.itemView.setBackgroundColor(Color.WHITE)
        val isMajorChecked = SharedPreferenceUtil.get(context, item, false)
        if (isMajorChecked) {
            holder.majorCheckImageView.setImageResource(R.drawable.ic_check_pink)
        } else {
            holder.majorCheckImageView.setImageResource(R.drawable.ic_check_white)
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var majorTextView = v.major_txt!!
        var majorCheckImageView = v.major_check_img
    }

    companion object {
        const val UNCHECKED = false
        const val CHECKED = true
    }
}
