package com.example.jiun.sookpam.web.department

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.util.RecyclerItemClickListener
import com.example.jiun.sookpam.user.setting.UserSettingLibrary
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import com.example.jiun.sookpam.web.common.WebRecyclerActivity
import kotlinx.android.synthetic.main.fragment_web_department.view.*

class WebDepartmentFragment : Fragment() {
    private lateinit var webDepartmentRecyclerView: RecyclerView
    private lateinit var departments: List<Department>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_web_department, container, false)
        initialize(view)
        return view
    }

    private fun initialize(view: View) {
        val departmentCategories = UserSettingLibrary.getSelectedMajors(context!!)
        departments = getDepartments(departmentCategories)
        webDepartmentRecyclerView = view.web_department_recycler
        webDepartmentRecyclerView.layoutManager = LinearLayoutManager(context)
        webDepartmentRecyclerView.adapter = WebDepartmentRecyclerAdapter(departments)
        webDepartmentRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(context, RecyclerItemClickListener.OnItemClickListener { _, position ->
            val intent = Intent(context, WebRecyclerActivity::class.java)
            intent.putExtra("category", departments[position].category)
            intent.putExtra("division", departments[position].division)
            if (departments[position].division == "공지") {
                intent.putExtra("background", R.drawable.department_notice)
            } else {
                intent.putExtra("background", R.drawable.department_career)
            }
            startActivity(intent)
        }))
    }

    private fun getDepartments(departmentCategories: ArrayList<String>): ArrayList<Department> {
        val departments = ArrayList<Department>()
        if (SharedPreferenceUtil.get(context, "취업", 0) == 2) {
            for (category in departmentCategories) {
                departments.add(Department(category, "공지"))
            }
        } else {
            for (category in departmentCategories) {
                departments.add(Department(category, "공지"))
                departments.add(Department(category, "취업"))
            }
        }
        return departments
    }

    data class Department(var category: String, var division: String)
}
