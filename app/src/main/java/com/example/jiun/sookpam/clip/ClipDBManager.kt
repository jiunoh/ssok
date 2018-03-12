package com.example.jiun.sookpam.clip


import com.example.jiun.sookpam.model.vo.DualVO
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

    fun select(): List<DualVO> {
        val voList = realm.where(DualVO::class.java).findAll()
        return voList.subList(0, voList.size);
    }
}
