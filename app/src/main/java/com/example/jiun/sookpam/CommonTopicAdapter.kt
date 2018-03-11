package com.example.jiun.sookpam

import android.content.Context
import com.example.jiun.sookpam.user.setting.SettingCategory
import com.example.jiun.sookpam.util.SharedPreferenceUtil

class CommonTopicAdapter {
    companion object {
        private const val INTEREST = "INTEREST"
        private const val NORMAL = "NORMAL"
        private const val SCHOLAR_DETAIL = "교내외 장학 소식을 제공합니다"
        private const val ACADEMIC_DETAIL = "학사일정 및 교내 주요 소식을 제공합니다"
        private const val EVENT_DETAIL = "교내 다양한 행사 소식을 제공합니다"
        private const val RECRUIT_DETAIL = "교내인턴, 동아리 회원모집 등의 소식을 제공합니다"
        private const val SYSTEM_DETAIL = "교내 IT 서비스 소식을 제공합니다"
        private const val GLOBAL_DETAIL = "국제협력팀의 소식을 제공합니다"
        private const val CAREER_DETAIL = "취업 관련 교내 행사 및 외부 소식을 제공합니다"
        private const val STUDENT_DETAIL = "학생지원팀의 소식을 제공합니다"

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
                    topics.add(CommonTopic(topic, getDetail(topic), topicStatus, getImage(topic)))
                }
            }
            return topics
        }

        private fun getDetail(topic: String): String {
            return when (topic) {
                "장학" -> SCHOLAR_DETAIL
                "학사" -> ACADEMIC_DETAIL
                "행사" -> EVENT_DETAIL
                "시스템" -> SYSTEM_DETAIL
                "국제" -> GLOBAL_DETAIL
                "모집" -> RECRUIT_DETAIL
                "취업" -> CAREER_DETAIL
                "학생" -> STUDENT_DETAIL
                else -> ""
            }
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