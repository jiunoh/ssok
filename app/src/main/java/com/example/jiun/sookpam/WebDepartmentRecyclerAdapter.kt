package com.example.jiun.sookpam

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
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
        holder!!.titleTextView.text = department.category
        if (department.division == "공지") {
            setImageByGlide(holder, R.drawable.department_notice)
            holder.divisionTextView.text = context.getString(R.string.notice)
        } else {
            setImageByGlide(holder, R.drawable.department_career)
            holder.divisionTextView.text = context.getString(R.string.career)
        }
    }

    private fun setImageByGlide(holder: ViewHolder, imageResource: Int) {
        Glide.with(context).asBitmap().load(imageResource)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        holder.departmentImageView.setImageBitmap(resource)
                    }
                })
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTextView = view.web_department_title_txt!!
        var divisionTextView = view.web_department_division_txt!!
        var departmentImageView = view.web_department_img!!
    }
}