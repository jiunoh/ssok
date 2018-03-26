package com.example.jiun.sookpam.web.department

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.util.AppGlideModule
import kotlinx.android.synthetic.main.common_topic_item.view.*

class WebDepartmentRecyclerAdapter(private val departments: List<WebDepartmentFragment.Department>?) : RecyclerView.Adapter<WebDepartmentRecyclerAdapter.ViewHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        context = parent!!.context
        val view = LayoutInflater.from(context).inflate(R.layout.common_topic_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return departments?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val department = departments!![position]
        holder!!.titleTextView.text = department.category
        if (department.division == "공지") {
            AppGlideModule().setImageByGlide(holder.departmentImageView, R.drawable.department_notice, context)
            holder.divisionTextView.text = context.getString(R.string.notice)
            holder.divisionTextView.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        } else {
            AppGlideModule().setImageByGlide(holder.departmentImageView, R.drawable.department_career, context)
            holder.divisionTextView.text = context.getString(R.string.career)
            holder.divisionTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryAccent))
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTextView = view.common_topic_title_txt!!
        var divisionTextView = view.common_topic_description_txt!!
        var departmentImageView = view.common_topic_img!!
    }
}