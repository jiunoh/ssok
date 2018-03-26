package com.example.jiun.sookpam.user.mypage.clip


import com.example.jiun.sookpam.model.DualModel
import com.example.jiun.sookpam.model.vo.DualVO
import io.realm.Realm

class ClipDBManager(val realm: Realm) {
    fun doesNotExist(value: String?): Boolean {
        var result = realm.where(DualVO::class.java).equalTo("title", value).findFirst()
        return (result == null)
    }

    fun insert(title: String, type: Int, date : String) {
        realm.executeTransaction { realm ->
            var record: DualVO = realm.createObject(DualVO::class.java)
            record.title = title
            record.type = type
            record.date = date
        }
    }

    fun insert(title: String, id :Int, type: Int) {
        realm.executeTransaction { realm ->
            var record: DualVO = realm.createObject(DualVO::class.java)
            record.title = title
            record.type = type
            record.db_id = id
        }
    }

    fun delete(title: String) {
        realm.executeTransaction { realm ->
            val result = realm.where(DualVO::class.java).equalTo("title", title).findAll()
            result.deleteAllFromRealm()
        }
    }

    fun select(): List<DualVO> {
        val voResult = realm.where(DualVO::class.java).findAll()
        var voList  = voResult.subList(0, voResult.size)
        return voList.sortedByDescending { it.date }
    }

    fun selectMessages(): List<DualVO> {
        val voResult = realm.where(DualVO::class.java).equalTo("type", DualModel.RECORD_VO).findAll()
        return voResult.subList(0, voResult.size)
    }

    fun selectWebs(): List<DualVO> {
        val voResult = realm.where(DualVO::class.java).equalTo("type", DualModel.RECORD_RESPONSE).findAll()
        return voResult.subList(0, voResult.size)
    }
}
