package com.example.jiun.sookpam

import com.example.jiun.sookpam.server.RecordResponse

data class SearchResponse(
        var search_keywords:ArrayList<String> = ArrayList(),
        var search_lists:ArrayList<RecordResponse> = ArrayList()
)