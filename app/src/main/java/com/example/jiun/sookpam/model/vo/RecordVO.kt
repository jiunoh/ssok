package com.example.jiun.sookpam.model.vo

import com.example.jiun.sookpam.searchable.SearchItem
import io.realm.RealmModel
import io.realm.annotations.RealmClass

@RealmClass
open class RecordVO : RealmModel, SearchItem() {
    override fun getListItemType(): Int {
        return SearchItem.RECORD_VO
    }

    var category: String? = null
    var division: String? = null
    var message: MessageVO? = null
}
