package com.example.jiun.sookpam.web

class WebRecordReformation {
    companion object {
        fun getTitleSubstring(title: String): String {
            val titleChars = title.toCharArray()
            var resultTitle:String = title
            for ((index, character) in titleChars.withIndex()) {
                if (character == ']') {
                    resultTitle = title.substring(index+1, title.lastIndex+1)
                    break
                }
            }
            return resultTitle
        }
    }
}