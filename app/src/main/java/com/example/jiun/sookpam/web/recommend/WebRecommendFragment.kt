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

class WebRecommendFragment : Fragment() {
    private lateinit var service: RecordService
    private lateinit var call: Call<List<RecordResponse>>
    private lateinit var webRecommendRecyclerView: RecyclerView
    private lateinit var connectErrorLinearLayout: LinearLayout
    private lateinit var connectErrorTextView: TextView
    private lateinit var refreshImageButton: ImageButton
    private lateinit var progressBar: ProgressBar
    private var records: List<RecordResponse>? = null
    private var isRefreshAlreadyStarted = false

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
        loadData()
        refreshImageButton = activity!!.web_base_refresh_img_btn
        refreshImageButton.setOnClickListener {
            val rotateAnimation = UIAnimation.setRotateAnimation(refreshImageButton)
            refreshImageButton.startAnimation(rotateAnimation)
            if (!isRefreshAlreadyStarted) {
                loadData()
            } else {
                CustomToast.showLastToast(context!!, getString(R.string.refresh_already_started))
            }
        }
    }

    private fun loadData() {
        val userInformation = UserInformation(context!!)
        call = service.getRecommendRecords(userInformation.studentGrade.split(" ")[0],
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
        progressBar.visibility = View.VISIBLE
        isRefreshAlreadyStarted = true
        call.enqueue(object : Callback<List<RecordResponse>> {
            override fun onFailure(call: Call<List<RecordResponse>>?, t: Throwable?) {
                showInternetConnectionError()
                isRefreshAlreadyStarted = false
            }

            override fun onResponse(call: Call<List<RecordResponse>>?, response: Response<List<RecordResponse>>?) {
                records = response!!.body()
                webRecommendRecyclerView.adapter = WebRecommendRecyclerAdapter(records)
                if (records!!.isNotEmpty()) {
                    UIAnimation.setLoadingRecyclerViewAnimation(webRecommendRecyclerView.context, webRecommendRecyclerView)
                }
                progressBar.visibility = View.INVISIBLE
                webRecommendRecyclerView.visibility = View.VISIBLE
                connectErrorLinearLayout.visibility = View.INVISIBLE
                isRefreshAlreadyStarted = false
            }
        })
    }

    private fun showInternetConnectionError() {
        if (isAdded) {
            progressBar.visibility = View.INVISIBLE
            connectErrorLinearLayout.visibility = View.INVISIBLE
            webRecommendRecyclerView.visibility = View.INVISIBLE
            connectErrorLinearLayout.visibility = View.VISIBLE
            connectErrorTextView.text = getString(R.string.internet_connect_error)
            CustomToast.showLastToast(context!!, getString(R.string.internet_connect_error))
        }
    }
}
