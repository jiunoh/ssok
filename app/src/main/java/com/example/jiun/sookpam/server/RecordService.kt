package com.example.jiun.sookpam.server

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RecordService {
    @GET("{category}/{division}")
    fun getRecords(
            @Path("category") category:String,
            @Path("division") division:String
    ): Call<List<RecordResponse>>
}
