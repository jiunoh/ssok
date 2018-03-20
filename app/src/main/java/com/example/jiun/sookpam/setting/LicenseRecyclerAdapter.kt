package com.example.jiun.sookpam.setting

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jiun.sookpam.R
import kotlinx.android.synthetic.main.license_item.view.*

class LicenseRecyclerAdapter(private val licenses: List<LicenseItem>) : RecyclerView.Adapter<LicenseRecyclerAdapter.ViewHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        context = parent!!.context
        val view = LayoutInflater.from(context).inflate(R.layout.license_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return licenses.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val license = licenses[position]
        holder!!.titleTextView.text = license.title
        holder.contentTextView.text = license.content
        holder.urlTextView.text = license.url
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTextView = view.license_item_title_txt
        var contentTextView = view.license_item_content_txt
        var urlTextView = view.license_item_url_txt
    }

}