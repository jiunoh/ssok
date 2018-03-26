package com.example.jiun.sookpam.user.info

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.user.mypage.CustomCategoryInterestItem
import kotlinx.android.synthetic.main.fragment_category_interest.view.*

class UserInfo3Fragment : Fragment() {
    private val customCategoryList = ArrayList<CustomCategoryInterestItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_info3, container, false)
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
