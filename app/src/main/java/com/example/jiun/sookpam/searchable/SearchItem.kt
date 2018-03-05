package com.example.jiun.sookpam.searchable

interface SearchItem {
    companion object {
        val RECORD_VO : Int = 1
        val RECORD_RESPONSE :Int = 2
    }
   fun getListItemType() : Int
}
