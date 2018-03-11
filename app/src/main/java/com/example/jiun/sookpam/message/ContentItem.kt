package com.example.jiun.sookpam.message

import java.io.Serializable
import java.util.*

class ContentItem : Serializable{
    lateinit var  body: String

    var phone: String? = null

    var division: String? = null

    var category: String? = null
}
