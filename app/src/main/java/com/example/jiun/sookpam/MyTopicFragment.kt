package com.example.jiun.sookpam

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.jiun.sookpam.user.setting.SettingCategory
import kotlinx.android.synthetic.main.fragment_my_topic.*

class MyTopicFragment : Fragment() {
    private var topicButtons: ArrayList<Button> = ArrayList()
    private var settingCategory: SettingCategory? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_topic, container, false)
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    private fun initialize() {
        settingCategory = SettingCategory(context!!)
        topicButtons.add(my_topic_scholarship_btn)
        topicButtons.add(my_topic_academic_btn)
        topicButtons.add(my_topic_entrance_btn)
        topicButtons.add(my_topic_recruit_btn)
        topicButtons.add(my_topic_system_btn)
        topicButtons.add(my_topic_global_btn)
        topicButtons.add(my_topic_career_btn)
        topicButtons.add(my_topic_student_btn)
        for (button in topicButtons) button.setOnClickListener {
            settingCategory!!.setCategoryButtonListener(button, getString(R.string.interest_category_min))
        }
        settingCategory!!.setColorsOf(topicButtons)
    }

    companion object {
        fun newInstance(): MyTopicFragment {
            val fragment = MyTopicFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
