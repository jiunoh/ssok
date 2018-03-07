package com.example.jiun.sookpam.clip

import com.example.jiun.sookpam.model.vo.ClipVO
import io.realm.Realm

class ClipDBManager(val realm: Realm) {
    private val COUNT = 25

    fun doesNotExist(value: String?): Boolean {
        var result = realm.where(ClipVO::class.java).equalTo("title", value).findFirst()
        return (result == null)
    }

    fun insert(title: String) {
        realm.executeTransaction { realm ->
            var record: ClipVO = realm.createObject(ClipVO::class.java)
            record.title = title
            record.status = true
        }
    }

    fun insert(title: String, location: String) {
        realm.executeTransaction { realm ->
            var record: ClipVO = realm.createObject(ClipVO::class.java)
            record.title = title
            record.status = true
            record.category = location
        }
    }

    fun delete(title: String) {
        realm.executeTransaction { realm ->
            val result = realm.where(ClipVO::class.java).equalTo("title", title).findAll()
            result.deleteAllFromRealm()
        }
    }

    fun select() : List<ClipVO> {
        var result : ArrayList<ClipVO> = ArrayList<ClipVO>()
        realm.executeTransaction { realm ->
            var list = realm.where(ClipVO::class.java).equalTo("status",true).findAll().subList(0, COUNT)
            result.addAll(list)
        }
        return result
    }
}