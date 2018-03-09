package com.example.jiun.sookpam.clip

import com.example.jiun.sookpam.model.vo.DualVO
import com.example.jiun.sookpam.model.vo.RecordVO
import com.example.jiun.sookpam.model.DualModel
import com.example.jiun.sookpam.server.WebFilter
import io.realm.Realm

class ClipDBManager(val realm: Realm) {
    fun doesNotExist(value: String?): Boolean {
        var result = realm.where(DualVO::class.java).equalTo("title", value).findFirst()
        return (result == null)
    }

    fun insert(title: String, type: Int) {
        realm.executeTransaction { realm ->
            var record: DualVO = realm.createObject(DualVO::class.java)
            record.title = title
            record.type = type
        }
    }

    fun delete(title: String) {
        realm.executeTransaction { realm ->
            val result = realm.where(DualVO::class.java).equalTo("title", title).findAll()
            result.deleteAllFromRealm()
        }
    }

    private fun getModelBy(record: DualVO): DualModel? {
        if (record.type == DualModel.RECORD_VO) {
            return realm.where(RecordVO::class.java).contains("message.body", record.title).findFirst()
        } else if(record.type == DualModel.RECORD_RESPONSE){
            return WebFilter.webFilter(record.title)[0]
        }
        else
            return null
    }

    fun select(): ArrayList<DualModel> {
        var result: ArrayList<DualModel> = ArrayList<DualModel>()
        var voList = realm.where(DualVO::class.java).findAll()
        for (clip in voList) {
            var model = getModelBy(clip)
            if(model != null)
                result.add(model)
        }
        return result
    }
}
