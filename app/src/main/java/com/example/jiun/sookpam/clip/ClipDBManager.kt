package com.example.jiun.sookpam.clip

import android.util.Log
import android.widget.Toast
import com.example.jiun.sookpam.model.vo.DualVO
import com.example.jiun.sookpam.model.vo.RecordVO
import com.example.jiun.sookpam.model.DualModel
import com.example.jiun.sookpam.server.ApiUtils
import com.example.jiun.sookpam.server.RecordResponse
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            try {
                return webFilter(record.title)[0]
            } catch (exception : IndexOutOfBoundsException ){
                Log.v("getModelBy","데이터가 없습니다.");
                return null
            }
        }
        else
            return null
    }

    private fun webFilter(charText: String) : ArrayList<RecordResponse>{
        val service = ApiUtils.getSearchableService()
        val query = charText.replace("\\s+".toRegex(), "-")
        var tempList :  ArrayList<RecordResponse> = ArrayList()
        service.getItems(query).enqueue(object : Callback<List<RecordResponse>> {
            override fun onResponse(call: Call<List<RecordResponse>>, response: Response<List<RecordResponse>>){
                if (!response.isSuccessful) {
                    Log.v("response", " disconnected")
                    return
                }
                Log.v("response.body>", response.body().toString())
                val size = response.body()!!.size
                tempList.addAll(response.body()!!.subList(0, size))
            }

            override fun onFailure(call: Call<List<RecordResponse>>, t: Throwable) {
                Log.v("onFailure:", "onFailure")
            }
        })

        return tempList
    }

    fun select(): ArrayList<DualModel> {
        var result: ArrayList<DualModel> = ArrayList<DualModel>()
        var voList = realm.where(DualVO::class.java).findAll()
        for (clip in voList) {
            var model = getModelBy(clip)
            if(model != null)
                result.add(model)
            //Log.v("> ", clip.toString())
        }
        return result
    }
}
