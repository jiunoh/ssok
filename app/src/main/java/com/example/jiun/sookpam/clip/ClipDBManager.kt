package com.example.jiun.sookpam.clip

import android.app.Application
import com.example.jiun.sookpam.model.vo.ClipVO
import io.realm.Realm

class ClipDBManager : Application()  {
    lateinit var realm: Realm

    override fun onCreate() {
        super.onCreate()
        try {
            Realm.init(this)
            realm = Realm.getDefaultInstance()
        }
        catch (exception: RuntimeException) {
            //Realm already exist
        }
    }

    fun doesNotExist(value: String?): Boolean {
        var result = realm.where(ClipVO::class.java).equalTo("title", value).findFirst()
        return (result == null)
    }

    fun insert(title: String) {
        realm.executeTransaction { realm ->
            var record : ClipVO = realm.createObject(ClipVO::class.java)
            record.title = title
            record.status = true
        }
    }

    fun delete(title: String) {
        realm.executeTransaction { realm ->
            val result = realm.where(ClipVO::class.java).equalTo("title", title).findAll()
            result.deleteAllFromRealm()
        }
    }
}