package com.example.jiun.sookpam

import android.os.Environment
import android.util.Log
import com.example.jiun.sookpam.model.vo.RecordVO
import com.example.jiun.sookpam.model.vo.RecordVOSerializer
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.realm.Realm
import io.realm.RealmObject
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.Writer

class RealmToJson(val realm: Realm) {
    private fun buildGson(): Gson {
        return GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
            override fun shouldSkipClass(clazz: Class<*>): Boolean {
                return false
            }

            override fun shouldSkipField(f: FieldAttributes): Boolean {
                return f.declaringClass == RealmObject::class.java
            }
        })
                .registerTypeAdapter(Class.forName("io.realm.RecordVORealmProxy"), RecordVOSerializer())
                .create()
    }

    fun convertRealmToJson(): String? {
        return buildGson().toJson(realm.where(RecordVO::class.java)
                        .notEqualTo("category", "VOID")
                        .findAll())
    }

    fun saveJsonFile(json:String) {
        try {
            lateinit var output: Writer
            val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"sookpam.json")
            output = BufferedWriter(FileWriter(file))
            output.write(json)
            output.close()
        } catch (e: Exception) {
            Log.e("RealmToJson.kt", "fail to save json file")
        }
    }
}