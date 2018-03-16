package com.example.jiun.sookpam.web

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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
    private lateinit var recommendView1: TextView
    private lateinit var recommendView2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_content)
        initialize()
        setContentData()
        setToolbar()
        requestRecommends()
    }

    private fun initialize() {
        idTextView = web_content_id_txt
        dateTextView = web_content_date_txt
        titleTextView = web_content_title_txt
        expandableLinear = web_content_attach_linear
        expandableAttachImageView = web_content_attach_expandable_img
        attachFilesCountTextView = web_content_attach_files_count_txt
        attachFilesTextView = web_content_attach_files_txt
        recommendView1 = recommend_view1
        recommendView2 = recommend_view2
        setExpandListener()
        setWebView()
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
    }

    private fun setContentData() {
        val intent = intent
        val record = intent.getSerializableExtra("record") as RecordResponse
        category = record.category
        division = record.division
        idTextView.text = record.id.toString()
        dateTextView.text = record.date
        titleTextView.text = WebRecordReformation.getTitleSubstring(record.title, record.category, record.division)
        contentWebView.loadData(record.content, "text/html; charset=utf-8", "UTF-8")
        checkAttachFilesAndApplyView(record)
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

    private fun setToolbar() {
        toolbar = web_content_toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar.setTitleTextColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
        toolbar.title = "웹 > ${category} > ${division}"
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener({
            finish()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_content, menu)
        var star = menu!!.findItem(R.id.action_star)
        dbmanager = ClipDBManager(Realm.getDefaultInstance());
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

    private fun requestRecommends() {
        val service = ApiUtils.getNgramService()
        val title = titleTextView.text.toString().replace("\\s+".toRegex(), "-")
        service.getItems(title).enqueue(object : Callback<List<RecordResponse>> {
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

    private fun setRequestedRecommends(records : List<RecordResponse>?) {
       recommendView1.text = records!![0].title
        recommendView1.setOnClickListener({
            val intent = Intent(applicationContext, WebContentActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("record", records!![0])
            intent!!.putExtras(bundle)
            startActivity(intent)
        })
        recommendView2.text = records!![1].title
        recommendView2.setOnClickListener({
            val intent = Intent(applicationContext, WebContentActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("record", records!![1])
            intent!!.putExtras(bundle)
            startActivity(intent)
        })
    }
}
