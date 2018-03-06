@file:JvmName("ApiUtils")

package com.example.jiun.sookpam.server

class ApiUtils {
    companion object {
        const val BASE_URL = "http://ec2-13-125-149-136.ap-northeast-2.compute.amazonaws.com:5000/"

        fun getRecordService():RecordService {
            return RetrofitClient.getClient(BASE_URL).create(RecordService::class.java)
        }

        fun getSearchableService() : SearchableService{
            return RetrofitClient.getClient(BASE_URL+"search/").create(SearchableService::class.java)
        }
    }
}
