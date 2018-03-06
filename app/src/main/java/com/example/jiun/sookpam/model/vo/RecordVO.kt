package com.example.jiun.sookpam.model.vo


import android.support.v7.widget.RecyclerView
import com.example.jiun.sookpam.searchable.SearchItem
import com.example.jiun.sookpam.util.ViewHolderFactory
import com.example.jiun.sookpam.util.ViewHolderFactory.RealmHolder
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class RecordVO : RealmModel, SearchItem {
    override fun getItemViewType(): Int {
        return SearchItem.RECORD_VO
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder) {
        var realmHolder: RealmHolder = viewHolder as RealmHolder
        realmHolder.category.text = category
        realmHolder.division.text = division
        val body = message!!.body
        val title = body.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        realmHolder.title.setText(title)
    }

    var category: String? = null
    var division: String? = null
    var message: MessageVO? = null
}
