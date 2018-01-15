package com.example.jiun.sookpam.model.mms

import com.example.jiun.sookpam.model.data.MmsVO
import io.realm.Realm
import io.realm.RealmResults
import java.util.*

class MmsList {
    private var realm: Realm = Realm.getDefaultInstance()
    private val mmsList: RealmResults<MmsVO> = getMmsList()

    fun getMmsList(): RealmResults<MmsVO> {
        return realm.where(MmsVO::class.java).findAll()
    }

    fun addMmsToList(phoneNumber: String?, date: Date?, body: String?) {
        realm.beginTransaction()
        val mms: MmsVO = realm.createObject(MmsVO::class.java, mmsList.size.toLong())
        mms.apply {
            this.phoneNumber = phoneNumber
            this.date = date
            this.body = body
        }
        realm.commitTransaction()
    }

    fun getSizeOfBody(body: String) : Int {
        val selectedBody = realm.where(MmsVO::class.java).contains("body", body).findAll()
        return selectedBody.size
    }
}