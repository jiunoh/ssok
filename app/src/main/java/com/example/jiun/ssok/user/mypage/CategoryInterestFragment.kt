package com.example.jiun.ssok.user.mypage


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jiun.ssok.R

import kotlinx.android.synthetic.main.fragment_category_interest.view.*


class CategoryInterestFragment : Fragment() {
    private val customCategoryList = ArrayList<CustomCategoryInterestItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_category_interest, container, false)
        initializeCustomCategories(view)
        return view
    }

    private fun initializeCustomCategories(view: View) {
        customCategoryList.add(view.category_academic_custom)
        customCategoryList.add(view.category_career_custom)
        customCategoryList.add(view.category_event_custom)
        customCategoryList.add(view.category_global_custom)
        customCategoryList.add(view.category_scholarship_custom)
        customCategoryList.add(view.category_recruit_custom)
        customCategoryList.add(view.category_system_custom)
        customCategoryList.add(view.category_student_custom)
    }
}
