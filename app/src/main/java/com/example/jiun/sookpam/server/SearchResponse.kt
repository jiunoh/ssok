package com.example.jiun.sookpam.server

import com.google.gson.annotations.SerializedName

data class SearchResponse(
        @SerializedName("search_keywords")
        var search_keywords: ArrayList<String>,
        @SerializedName("search_list")
        var search_list: ArrayList<RecordResponse>
)