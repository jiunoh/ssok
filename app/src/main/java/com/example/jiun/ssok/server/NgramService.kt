package com.example.jiun.ssok.server

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NgramService {
    @GET("ngram/{query}")
    fun getItems(
            @Path("query") query: String
    ): Call<List<RecordResponse>>
}
