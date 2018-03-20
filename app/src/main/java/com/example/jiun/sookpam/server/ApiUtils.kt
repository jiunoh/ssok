@file:JvmName("ApiUtils")

package com.example.jiun.sookpam.server

class ApiUtils {
    companion object {
        const val BASE_URL = "http://ec2-52-79-187-67.ap-northeast-2.compute.amazonaws.com/"

        fun getRecordService():RecordService {
            return RetrofitClient.getClient(BASE_URL).create(RecordService::class.java)
        }

        fun getSearchableService() : SearchableService{
            return RetrofitClient.getClient(BASE_URL).create(SearchableService::class.java)
        }

        fun getNgramService() : NgramService {
            return RetrofitClient.getClient(BASE_URL).create(NgramService ::class.java)
        }

        fun getSearchKeywordService() : SearchKeywordService {
            return RetrofitClient.getClient(BASE_URL).create(SearchKeywordService::class.java)
        }

        fun getClipService() : ClipService {
            return RetrofitClient.getClient(BASE_URL).create(ClipService::class.java)
        }
    }
}
