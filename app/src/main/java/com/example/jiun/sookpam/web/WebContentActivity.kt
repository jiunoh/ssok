package com.example.jiun.sookpam.web

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.clip.ClipDBManager
import com.example.jiun.sookpam.model.DualModel
import com.example.jiun.sookpam.server.ApiUtils
import com.example.jiun.sookpam.server.RecordResponse
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_web_content.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WebContentActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var idTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var titleTextView: TextView
    private lateinit var category: String
    private lateinit var division: String
    private lateinit var dbmanager: ClipDBManager
    private lateinit var contentWebView: WebView
    private lateinit var expandableLinear: LinearLayout
    private lateinit var expandableAttachImageView: ImageView
    private lateinit var attachFilesCountTextView: TextView
    private lateinit var attachFilesTextView: TextView
    private lateinit var headerRelativeLayout: RelativeLayout
    private lateinit var recommendLinearLayout: LinearLayout
    private lateinit var recommendView1: TextView
    private lateinit var recommendView2: TextView
    private lateinit var recommendExpandTextView: TextView
    private lateinit var recommendExpandFrame: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_content)
        initialize()
        setContentData()
        setToolbar()
    }

    private fun initialize() {
        idTextView = web_content_id_txt
        dateTextView = web_content_date_txt
        titleTextView = web_content_title_txt
        expandableLinear = web_content_attach_linear
        expandableAttachImageView = web_content_attach_expandable_img
        attachFilesCountTextView = web_content_attach_files_count_txt
        attachFilesTextView = web_content_attach_files_txt
        headerRelativeLayout = web_content_header_relative
        recommendLinearLayout = web_content_recommend_linear
        recommendView1 = recommend_view1
        recommendView2 = recommend_view2
        recommendExpandTextView = web_content_recommend_expand_txt
        recommendExpandFrame = web_content_recommend_expand_frame
        setRecommendExpandListener()
        setExpandListener()
        setWebView()
    }

    private fun setRecommendExpandListener() {
        recommendExpandFrame.setOnClickListener {
            if (recommendExpandTextView.text == getString(R.string.expand)) {
                recommendExpandTextView.text = getString(R.string.shrink)
                recommendView1.visibility = View.VISIBLE
                recommendView2.visibility = View.VISIBLE
            } else {
                recommendExpandTextView.text = getString(R.string.expand)
                recommendView1.visibility = View.GONE
                recommendView2.visibility = View.GONE
            }
        }
    }

    private fun setExpandListener() {
        expandableLinear.setOnClickListener {
            if (expandableAttachImageView.rotation == 0.0f) {
                attachFilesTextView.visibility = View.VISIBLE
                expandableAttachImageView.rotation = 180.0f
            } else {
                attachFilesTextView.visibility = View.GONE
                expandableAttachImageView.rotation = 0.0f
            }
        }
    }

    private fun setWebView() {
        contentWebView = web_content_web_view
        contentWebView.settings.builtInZoomControls = true
        contentWebView.settings.supportZoom()
        contentWebView.settings.displayZoomControls = false
        contentWebView.isScrollbarFadingEnabled = true
        contentWebView.isScrollContainer = false
        contentWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                contentWebView.loadUrl("javascript:AndroidFunction.resize(document.body.scrollHeight)")
            }
        }
        contentWebView.viewTreeObserver.addOnScrollChangedListener {
            if (contentWebView.scrollY == 0) {
                headerRelativeLayout.postDelayed({
                    headerRelativeLayout.visibility = View.VISIBLE
                }, 400)
            } else {
                headerRelativeLayout.postDelayed({
                    headerRelativeLayout.visibility = View.GONE
                    attachFilesTextView.visibility = View.GONE
                    expandableAttachImageView.rotation = 0.0f
                }, 400)
            }
        }

    }

    private fun setContentData() {
        val intent = intent
        val record = intent.getSerializableExtra("record") as RecordResponse
        category = record.category
        division = record.division
        idTextView.text = record.id.toString()
        dateTextView.text = record.date
        titleTextView.text = WebRecordReformation.getTitleSubstring(record.title, record.category, record.division)
        contentWebView.loadData(record.content + "<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>",
                "text/html; charset=utf-8", "UTF-8")
        checkAttachFilesAndApplyView(record)
        requestRecommends(record.title)
    }

    private fun checkAttachFilesAndApplyView(record: RecordResponse) {
        val attachFilesShortCutHtml = WebRecordReformation.getAttachUrlShortcutHtml(record.attach)
        if (attachFilesShortCutHtml == null) {
            expandableAttachImageView.visibility = View.GONE
            expandableLinear.setOnClickListener(null)
            attachFilesCountTextView.text = "0"
        } else {
            attachFilesCountTextView.text = attachFilesShortCutHtml.size.toString()
            fromHtml(attachFilesTextView, attachFilesShortCutHtml)
        }
    }

    @SuppressWarnings("deprecation")
    private fun fromHtml(attachFilesTextView: TextView, attachFilesShortCutHtml: ArrayList<String>) {
        attachFilesTextView.let {
            it.autoLinkMask = 0
            it.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(attachFilesShortCutHtml.joinToString(""), Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(attachFilesShortCutHtml.joinToString(""))
            }
            it.movementMethod = LinkMovementMethod.getInstance()
        }
    }

    @SuppressLint("PrivateResource")
    private fun setToolbar() {
        toolbar = web_content_toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar.setTitleTextColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
        if (category == "공통") {
            toolbar.title = "웹 > 학교소식 > ${division}"
        } else {
            toolbar.title = "웹 > ${category} > ${division}"
        }
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener({
            finish()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_content, menu)
        val star = menu!!.findItem(R.id.action_star)
        dbmanager = ClipDBManager(Realm.getDefaultInstance())
        if (dbmanager.doesNotExist(titleTextView.text.toString())) {
            star.icon = resources.getDrawable(R.drawable.star_off)
        } else {
            star.icon = resources.getDrawable(R.drawable.star_on)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_star -> {
                val title = titleTextView.text.toString()
                if (dbmanager.doesNotExist(title)) {
                    item.icon = resources.getDrawable(R.drawable.star_on)
                    dbmanager.insert(title, DualModel.RECORD_RESPONSE, dateTextView.text.toString())
                } else {
                    item.icon = resources.getDrawable(R.drawable.star_off)
                    dbmanager.delete(title)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun requestRecommends(title: String) {
        val service = ApiUtils.getNgramService()
        val reformattedTitle = title.replace(" ", "--").replace("/", "__")
        service.getItems(reformattedTitle).enqueue(object : Callback<List<RecordResponse>> {
            override fun onResponse(call: Call<List<RecordResponse>>, response: Response<List<RecordResponse>>) {
                if (!response.isSuccessful) {
                    Log.v("response", " disconnected")
                    return
                }
                setRequestedRecommends(response.body())
            }

            override fun onFailure(call: Call<List<RecordResponse>>, t: Throwable) {
                Log.v("onFailure:", "onFailure")
            }
        })
    }

    private fun setRequestedRecommends(records: List<RecordResponse>?) {
        recommendView1.text = WebRecordReformation.getTitleSubstring(records!![0].title, records[0].category, records[0].division)
        recommendView1.setOnClickListener({
            val intent = Intent(applicationContext, WebContentActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("record", records[0])
            intent.putExtras(bundle)
            startActivity(intent)
        })
        recommendView2.text = WebRecordReformation.getTitleSubstring(records[1].title, records[1].category, records[1].division)
        recommendView2.setOnClickListener({
            val intent = Intent(applicationContext, WebContentActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("record", records[1])
            intent.putExtras(bundle)
            startActivity(intent)
        })
    }
}
