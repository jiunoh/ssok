package com.example.jiun.sookpam

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jiun.sookpam.web.WebRecordReformation
import kotlinx.android.synthetic.main.web_department_item.view.*

class WebDepartmentRecyclerAdapter(private val departments: List<WebDepartmentFragment.Department>?) : RecyclerView.Adapter<WebDepartmentRecyclerAdapter.ViewHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        context = parent!!.context
        val view = LayoutInflater.from(context).inflate(R.layout.web_department_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return departments?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val department = departments!![position]
        holder!!.titleTextView.text = WebRecordReformation.getTitleSubstring(department.category)
        if (department.division == "공지") {
            holder.departmentImageView.setImageResource(R.drawable.department_notice)
            holder.divisionTextView.text = context.getString(R.string.notice)
        } else {
            holder.departmentImageView.setImageResource(R.drawable.department_career)
            holder.divisionTextView.text = context.getString(R.string.career)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTextView = view.web_department_title_txt!!
        var divisionTextView = view.web_department_division_txt!!
        var departmentImageView = view.web_department_img
    }
}