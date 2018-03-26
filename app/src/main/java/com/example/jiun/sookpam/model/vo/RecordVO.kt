package com.example.jiun.sookpam.model.vo


import android.support.v7.widget.RecyclerView
import com.example.jiun.sookpam.model.DualModel
import com.example.jiun.sookpam.util.ViewHolderFactory.DualHolder
import io.realm.RealmModel
import io.realm.annotations.RealmClass
import java.text.SimpleDateFormat

@RealmClass
open class RecordVO : RealmModel, DualModel {
    var category: String? = null
    var division: String? = null
    var message: MessageVO? = null

    override fun toString(): String {
        val body = message!!.body
        val title = body.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[2]
        return title
    }

    override fun getItemViewType(): Int {
        return DualModel.RECORD_VO
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder) {
        var realmHolder = viewHolder as DualHolder
        if (realmHolder.categoryTextView != null) {
            realmHolder.categoryTextView.text = "문자-$category-$division"
            val body = message!!.body
            val title = body.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
            realmHolder.titleTextVIew.text = title
            val pattern = "yyyy-MM-dd"
            var simpleDateFormat = SimpleDateFormat(pattern)
            realmHolder.dateTextView.text = simpleDateFormat.format(message!!.date)
        }
    }
}
