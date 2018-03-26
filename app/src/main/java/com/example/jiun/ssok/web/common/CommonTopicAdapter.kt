package com.example.jiun.ssok.web.common

import android.content.Context
import com.example.jiun.ssok.R
import com.example.jiun.ssok.user.setting.SettingCategory
import com.example.jiun.ssok.util.SharedPreferenceUtil

class CommonTopicAdapter {
    companion object {
        private const val INTEREST = "관심 카테고리"
        private const val NORMAL = "일반 카테고리"

        fun getInterestOrNormalTopics(context: Context): List<CommonTopic> {
            val interestTopics = getTopics(context, INTEREST, 1)
            val normalTopics = getTopics(context, NORMAL, 0)
            if (normalTopics != null) {
                interestTopics!!.addAll(normalTopics)
            }
            return interestTopics!!
        }

        private fun getTopics(context: Context, topicStatus: String, statusNumber: Int): ArrayList<CommonTopic>? {
            val topics: ArrayList<CommonTopic> = ArrayList()
            for (topic in SettingCategory.categories) {
                if (SharedPreferenceUtil.get(context, topic, 0) == statusNumber) {
                    topics.add(CommonTopic(topic, topicStatus, getImage(topic)))
                }
            }
            return topics
        }

        private fun getImage(topic: String): Int {
            return when (topic) {
                "장학" -> R.drawable.scholarship
                "학사" -> R.drawable.academic
                "행사" -> R.drawable.event
                "시스템" -> R.drawable.system
                "국제" -> R.drawable.global
                "모집" -> R.drawable.recruit
                "취업" -> R.drawable.career
                "학생" -> R.drawable.student
                else -> 0
            }
        }
    }
}