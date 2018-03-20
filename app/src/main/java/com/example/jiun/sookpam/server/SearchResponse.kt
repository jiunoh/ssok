package com.example.jiun.sookpam.server

import com.example.jiun.sookpam.server.RecordResponse

data class SearchResponse(
        var search_keywords:ArrayList<String> = ArrayList(),
        var search_list:ArrayList<RecordResponse> = ArrayList()
)