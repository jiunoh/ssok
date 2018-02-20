package com.example.jiun.sookpam.model.vo


import io.realm.RealmObject

open class CategoryVO : RealmObject() {
    lateinit var key : String
    lateinit var  value : String
}