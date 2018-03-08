package com.example.jiun.sookpam.clip

import com.example.jiun.sookpam.model.vo.DualVO
import com.example.jiun.sookpam.model.vo.RecordVO
import com.example.jiun.sookpam.searchable.DualModel
import com.example.jiun.sookpam.server.RecordResponse
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

    private fun getDataBy(record: DualVO): DualModel {
        var data: DualModel = RecordResponse()
        if (record.type == DualModel.RECORD_VO) {
            realm.executeTransaction { realm ->
                var data = realm.where(RecordVO::class.java).contains("message.body", record.title).findFirst()
            }
        }
        else {
            data = WebFilter.webFilter(record.title)[0]
        }
        return data
    }

    fun select(clip: DualVO): ClipItem {
        var model = getDataBy(clip)
        var type: String

        if (clip.type == DualModel.RECORD_VO) {
            type = "메세지"
            model as RecordVO
            return ClipItem(clip.title, type + model.category + "-" + model.division)
        } else {
            type = "웹"
            model as RecordResponse
            return ClipItem(clip.title, type + model.category + "-" + model.division)
        }
    }

    fun select(): ArrayList<DualModel> {
        var result: ArrayList<DualModel> = ArrayList<DualModel>()
        realm.executeTransaction { realm ->
            var clipsList = realm.where(DualVO::class.java).findAll()
            for (clip in clipsList) {
                var model = getDataBy(clip)
                result.add(model)
            }
        }
        return result
    }
}
