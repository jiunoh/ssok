package com.example.jiun.sookpam.user.setting

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.*
import com.example.jiun.sookpam.user.mypage.MyInfoFragment
import com.example.jiun.sookpam.R
import kotlinx.android.synthetic.main.selected_major_recycler_item.view.*

class SelectedMajorRecyclerAdapter(val data: List<String>?) : RecyclerView.Adapter<SelectedMajorRecyclerAdapter.ViewHolder>() {
    lateinit var context: Context
    lateinit var activity: Activity
    lateinit var selectedMajors: ArrayList<String>

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        context = parent!!.context
        activity = context as Activity
        val view = LayoutInflater.from(context).inflate(R.layout.selected_major_recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val item = data!![position]
        holder!!.selectedMajorTextView.text = item
        holder.selectedMajorTextView.setOnClickListener {
            MyInfoFragment.selectMajors(context, activity)
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var selectedMajorTextView = v.selected_major_txt!!
    }
}
