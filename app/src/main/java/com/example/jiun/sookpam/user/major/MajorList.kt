package com.example.jiun.sookpam.user.major

import java.util.*

class MajorList {
    companion object {
        val liberalarts = ArrayList(Arrays.asList("한국어문학부", "역사문화학과", "프랑스언어문화학과", "중어중문학부", "독일언어문화학과", "일본학과", "문헌정보학과", "문화관광학부", "교육학부"))
        val science = ArrayList(Arrays.asList("화학과", "생명시스템학부", "수학과", "통계학과", "체육교육과", "무용과"))
        val engineering = ArrayList(Arrays.asList("화공생명공학부", "ICT융합공학부", "소프트웨어학부", "기계시스템학부", "기초공학부"))
        val humanEcology = ArrayList(Arrays.asList("가족자원경영학과", "아동복지학부", "의류학과", "식품영양학과"))
        val socialSciences = ArrayList(Arrays.asList("정치외교학과", "행정학과", "홍보광고학과", "소비자경제학과", "사회심리학과"))
        val law = ArrayList(Arrays.asList("법학부"))
        val economics = ArrayList(Arrays.asList("경제학부", "경영학부"))
        val music = ArrayList(Arrays.asList("피아노과", "관현악과", "성악과", "작곡과"))
        val pharmacy = ArrayList(Arrays.asList("약학부"))
        val fineArt = ArrayList(Arrays.asList("시각영상디자인과", "산업디자인과", "환경디자인과", "공예과", "회화과"))
        val global = ArrayList(Arrays.asList("글로벌협력전공", "앙트러프러너십전공"))
        val english = ArrayList(Arrays.asList("영어영문학전공", "테슬(TESL)전공"))
        val media = ArrayList(Arrays.asList("미디어학부"))

        val collegeAndMajors = ArrayList(Arrays.asList(liberalarts, science, engineering, humanEcology, socialSciences, law, economics, music, pharmacy, fineArt, global, english, media))
    }
}
