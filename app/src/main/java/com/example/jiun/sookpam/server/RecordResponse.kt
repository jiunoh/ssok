package com.example.jiun.sookpam.server

import java.io.Serializable

data class RecordResponse(
    var id:Int = 0,
    var view:Int = 0,
    var category:String = "",
    var division:String = "",
    var url:String = "",
    var date:String = "",
    var title:String = "",
    var content:String = ""
):Serializable
