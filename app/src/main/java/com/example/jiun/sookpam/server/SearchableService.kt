package com.example.jiun.sookpam.server

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchableService {
    @GET("search/{query}")
    fun getItems(
            @Path("query") query: String
    ): Call<List<RecordResponse>>
}
