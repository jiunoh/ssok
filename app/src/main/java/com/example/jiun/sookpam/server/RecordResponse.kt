package com.example.jiun.sookpam.server

import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.clip.ClipDBManager
import com.example.jiun.sookpam.model.DualModel
import com.example.jiun.sookpam.util.ViewHolderFactory
import io.realm.Realm
import java.io.Serializable

data class RecordResponse(
        var id: Int = 0,
        var view: Int = 0,
        var category: String = "",
        var division: String = "",
        var url: String = "",
        var date: String = "",
        var title: String = "",
        var content: String = "",
        var attach: String = ""
) : Serializable, DualModel {

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder) {
        val webHolder = viewHolder as ViewHolderFactory.DualHolder
        webHolder.categoryTextView.text = "웹-$category-$division"
        webHolder.titleTextVIew.text = title
        webHolder.dateTextView.text = date
    }


    private fun setStarIcon(view : ImageView, title : String) {
        val dbmanager = ClipDBManager(Realm.getDefaultInstance());
        if (dbmanager.doesNotExist(title)) {
            view.setImageResource(R.drawable.star_off)
        } else {
            view.setImageResource(R.drawable.star_on)
        }
    }

    override fun getItemViewType(): Int {
        return DualModel.RECORD_RESPONSE
    }

    override fun toString(): String {
        return title
    }
}