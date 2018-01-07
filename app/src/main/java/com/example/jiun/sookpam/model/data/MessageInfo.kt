package com.example.jiun.sookpam.model.data

import com.example.jiun.sookpam.R

open class MessageInfo {
    companion object {
        val SMS: Boolean = true
        val MMS: Boolean = false
        val CATEGORY_DEFAULT = R.string.message_info_category_default.toString()
    }
}