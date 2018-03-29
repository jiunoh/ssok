package com.example.jiun.ssok.web.recommend

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.jiun.ssok.R
import com.example.jiun.ssok.server.RecordResponse
import com.example.jiun.ssok.web.WebRecordReformation
import kotlinx.android.synthetic.main.web_recommend_recycler_item.view.*

class WebRecommendRecyclerAdapter(private val records: List<RecordResponse>?) : RecyclerView.Adapter<WebRecommendRecyclerAdapter.ViewHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        context = parent!!.context
        val view = LayoutInflater.from(context).inflate(R.layout.web_recommend_recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return records?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val record = records!![position]
        holder!!.titleTextView.text = WebRecordReformation.getTitleSubstring(record.title, record.category, record.division)
        holder.categoryTextView.text = record.category
        holder.divisionTextView.text = record.division
        holder.dateTextView.text = record.date
        when (position) {
            0 -> holder.rankImageView.setImageResource(R.drawable.rank1)
            1 -> holder.rankImageView.setImageResource(R.drawable.rank2)
            2 -> holder.rankImageView.setImageResource(R.drawable.rank3)
            else -> holder.rankImageView.setImageResource(R.drawable.rank_image)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleTextView = view.recommend_title_txt!!
        var categoryTextView = view.recommend_category_txt!!
        var divisionTextView = view.recommend_division_txt!!
        var dateTextView = view.recommend_date_txt!!
        var rankImageView = view.recommend_rank_img_view!!
    }
}