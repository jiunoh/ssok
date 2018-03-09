package com.example.jiun.sookpam.web.recommend

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.*
import android.view.*
import android.widget.*
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.*
import com.example.jiun.sookpam.server.*
import com.example.jiun.sookpam.user.UserInformation
import com.example.jiun.sookpam.web.WebContentActivity
import kotlinx.android.synthetic.main.fragment_web_base.*
import kotlinx.android.synthetic.main.fragment_web_recommend.view.*
import retrofit2.*

class WebRecommendRecyclerFragment : Fragment() {
    private lateinit var service: RecordService
    private lateinit var webRecommendRecyclerView: RecyclerView
    private lateinit var connectErrorLinearLayout: LinearLayout
    private lateinit var connectErrorTextView: TextView
    private lateinit var refreshImageButton: ImageButton
    private lateinit var progressBar: ProgressBar
    private var records: List<RecordResponse>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_web_recommend, container, false)
        initialize(view)
        return view
    }

    private fun initialize(view: View) {
        connectErrorLinearLayout = view.web_recommend_error_linear
        connectErrorTextView = view.web_recommend_error_txt
        progressBar = view.web_recommend_progressbar
        webRecommendRecyclerView = view.web_recommend_recycler_view
        webRecommendRecyclerView.layoutManager = LinearLayoutManager(context)
        webRecommendRecyclerView.adapter = WebRecommendRecyclerAdapter(null)
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
                userInformation.interestEvent,
                userInformation.interestRecruit,
                userInformation.interestSystem,
                userInformation.interestGlobal,
                userInformation.interestCareer,
                userInformation.interestStudent)
        refreshImageButton = activity!!.web_base_refresh_img_btn
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
                    userInformation.interestEvent,
                    userInformation.interestRecruit,
                    userInformation.interestSystem,
                    userInformation.interestGlobal,
                    userInformation.interestCareer,
                    userInformation.interestStudent)
        }
    }

    private fun loadData(studentGrade: String, studentYear: String, major1: String, major2: String,
                         schoolScholar: Boolean, governmentScholar: Boolean, externalScholar: Boolean,
                         studentStatus: Boolean, interestScholarship: Int, interestAcademic: Int,
                         interestEvent: Int, interestRecruit: Int, interestSystem: Int,
                         interestGlobal: Int, interestCareer: Int, interestStudent: Int) {
        service.getRecommendRecords(studentGrade.split(" ")[0], studentYear, major1, major2, schoolScholar,
                governmentScholar, externalScholar, studentStatus, interestScholarship, interestAcademic,
                interestEvent, interestRecruit, interestSystem, interestGlobal, interestCareer,
                interestStudent).enqueue(object : Callback<List<RecordResponse>> {
            override fun onFailure(call: Call<List<RecordResponse>>?, t: Throwable?) {
                showInternetConnectionError()
            }

            override fun onResponse(call: Call<List<RecordResponse>>?, response: Response<List<RecordResponse>>?) {
                progressBar.visibility = View.VISIBLE
                records = response!!.body()
                webRecommendRecyclerView.adapter = WebRecommendRecyclerAdapter(records)
                if (records!!.isNotEmpty()) {
                    UIAnimation.setLoadingRecyclerViewAnimation(webRecommendRecyclerView.context, webRecommendRecyclerView)
                }
                progressBar.visibility = View.INVISIBLE
                webRecommendRecyclerView.visibility = View.VISIBLE
                connectErrorLinearLayout.visibility = View.INVISIBLE
            }
        })
    }

    private fun showInternetConnectionError() {
        connectErrorLinearLayout.visibility = View.INVISIBLE
        webRecommendRecyclerView.visibility = View.INVISIBLE
        connectErrorLinearLayout.visibility = View.VISIBLE
        connectErrorTextView.text = getString(R.string.internet_connect_error)
    }
}
