package com.example.jiun.sookpam.web

class WebRecordReformation {
    companion object {
        fun getTitleSubstring(title: String, category: String, division: String): String {
            if (category != "공통" && category != "취업")
                return title
            else if (division == "국제") return title
            val titleChars = title.toCharArray()
            var resultTitle: String = title
            for ((index, character) in titleChars.withIndex()) {
                if (character == ']') {
                    resultTitle = if (index < titleChars.size - 1 && titleChars[index + 1] == ' ') {
                        title.substring(index + 2, title.lastIndex + 1)
                    } else {
                        title.substring(index + 1, title.lastIndex + 1)
                    }
                    break
                }
            }
            return resultTitle
        }

        fun getAttachUrlShortcutHtml(attach: String): ArrayList<String>? {
            if (attach.isEmpty()) return null
            val htmlAttachShortcut: ArrayList<String> = ArrayList()
            val attachList = attach.split(',')
            for (i in 0..attachList.size step 2) {
                if (i + 1 < attachList.size) {
                    htmlAttachShortcut.add("<a href=\"${attachList[i + 1]}\">${attachList[i].replace(" 다운로드","")}</a><br/>")
                } else {
                    break
                }
            }
            return htmlAttachShortcut
        }
    }
}