package com.example.jiun.sookpam.web.recommend

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.*
import android.view.*
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.*
import com.example.jiun.sookpam.server.*
import com.example.jiun.sookpam.user.UserInformation
import com.example.jiun.sookpam.web.WebContentActivity
import kotlinx.android.synthetic.main.activity_view_pager_main.*
import kotlinx.android.synthetic.main.fragment_web_recommend.*
import retrofit2.*

class WebRecommendFragment : Fragment() {
    private lateinit var service: RecordService
    private lateinit var webRecommendRecyclerView: RecyclerView
    private lateinit var connectErrorLinearLayout: LinearLayout
    private lateinit var connectErrorTextView: TextView
    private lateinit var refreshImageButton: ImageButton
    private var records: List<RecordResponse>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_web_recommend, container, false)
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    private fun initialize() {
        connectErrorLinearLayout = web_recommend_error_linear
        connectErrorTextView = web_recommend_error_txt
        webRecommendRecyclerView = web_recommend_recycler_view
        webRecommendRecyclerView.layoutManager = LinearLayoutManager(context)
        webRecommendRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(context, RecyclerItemClickListener.OnItemClickListener { _, position ->
            val intent = Intent(context, WebContentActivity::class.java)
            intent.putExtra("record", records!![position])
            startActivity(intent)
        }))
        service = ApiUtils.getRecordService()
        val userInformation = UserInformation(context!!)
        loadData(userInformation.studentGrade,
                userInformation.studentYear,
                userInformation.major1,
                userInformation.major2,
                userInformation.schoolScholar,
                userInformation.governmentScholar,
                userInformation.externalScholar,
                userInformation.studentStatus,
                userInformation.interestScholarship,
                userInformation.interestAcademic,
                userInformation.interestEntrance,
                userInformation.interestRecruit,
                userInformation.interestSystem,
                userInformation.interestGlobal,
                userInformation.interestCareer,
                userInformation.interestStudent)
        refreshImageButton = activity!!.main_refresh_image_btn
        refreshImageButton.setOnClickListener {
            val rotateAnimation = UIAnimation.setRotateAnimation(refreshImageButton)
            refreshImageButton.startAnimation(rotateAnimation)
            loadData(userInformation.studentGrade,
                    userInformation.studentYear,
                    userInformation.major1,
                    userInformation.major2,
                    userInformation.schoolScholar,
                    userInformation.governmentScholar,
                    userInformation.externalScholar,
                    userInformation.studentStatus,
                    userInformation.interestScholarship,
                    userInformation.interestAcademic,
                    userInformation.interestEntrance,
                    userInformation.interestRecruit,
                    userInformation.interestSystem,
                    userInformation.interestGlobal,
                    userInformation.interestCareer,
                    userInformation.interestStudent)
        }
    }

    private fun loadData(studentGrade: Int, studentYear: Int, major1: String, major2: String,
                         schoolScholar: Boolean, governmentScholar: Boolean, externalScholar: Boolean,
                         studentStatus: Boolean, interestScholarship: Int, interestAcademic: Int,
                         interestEntrance: Int, interestRecruit: Int, interestSystem: Int,
                         interestGlobal: Int, interestCareer: Int, interestStudent: Int) {
        service.getRecommendRecords(studentGrade, studentYear, major1, major2, schoolScholar,
                governmentScholar, externalScholar, studentStatus, interestScholarship, interestAcademic,
                interestEntrance, interestRecruit, interestSystem, interestGlobal, interestCareer,
                interestStudent).enqueue(object : Callback<List<RecordResponse>> {
            override fun onFailure(call: Call<List<RecordResponse>>?, t: Throwable?) {
                showInternetConnectionError()
            }

            override fun onResponse(call: Call<List<RecordResponse>>?, response: Response<List<RecordResponse>>?) {
                records = response!!.body()
                webRecommendRecyclerView.adapter = WebRecommendRecyclerAdapter(records)
                if (records!!.isNotEmpty()) {
                    UIAnimation.setLoadingRecyclerViewAnimation(webRecommendRecyclerView.context, webRecommendRecyclerView)
                }
            }
        })
    }

    private fun showInternetConnectionError() {
        Toast.makeText(context, getString(R.string.internet_connect_error), Toast.LENGTH_SHORT).show()
        webRecommendRecyclerView.visibility = View.INVISIBLE
        connectErrorLinearLayout.visibility = View.VISIBLE
        connectErrorTextView.text = getString(R.string.internet_connect_error)
    }

    companion object {
        fun newInstance(): WebRecommendFragment {
            val fragment = WebRecommendFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
