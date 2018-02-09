package com.example.jiun.sookpam.model.data


import io.realm.RealmObject

open class CategoryVO : RealmObject() {
    lateinit var key : String
    lateinit var  value : String
}