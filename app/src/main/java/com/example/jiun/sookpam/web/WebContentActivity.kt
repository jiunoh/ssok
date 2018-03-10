package com.example.jiun.sookpam.web

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.clip.ClipDBManager
import com.example.jiun.sookpam.model.DualModel
import com.example.jiun.sookpam.server.RecordResponse
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_web_content.*

class WebContentActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var categoryTextView: TextView
    lateinit var divisionTextView: TextView
    lateinit var idTextView: TextView
    lateinit var dateTextView: TextView
    lateinit var urlTextView: TextView
    lateinit var titleTextView: TextView
    lateinit var contentTextView: TextView
    private lateinit var dbmanager: ClipDBManager
    private lateinit var title: String
    private lateinit var body: String
    private lateinit var category: String
    private lateinit  var division: String


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
        urlTextView = web_content_url_txt
        titleTextView = web_content_title_txt
        contentTextView = web_content_content_txt
    }

    private fun setContentData() {
        val intent = intent
        val record = intent.getSerializableExtra("record") as RecordResponse
        category = record.category
        division = record.division
        idTextView.text = record.id.toString()
        dateTextView.text = record.date
        urlTextView.text = record.url
        titleTextView.text = WebRecordReformation.getTitleSubstring(record.title, record.category, record.division)
        title = titleTextView.text.toString()
        contentTextView.text = record.content.replace("[\n", "").replace("\n]", "").replace("\n\n", "\n")
    }

    private fun setToolbar() {
        toolbar = web_content_toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbar.setTitleTextColor(resources.getColor(R.color.colorPrimary))
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
        if (dbmanager.doesNotExist(title)) {
            star.icon = resources.getDrawable(R.drawable.star_off)
        } else {
            star.icon = resources.getDrawable(R.drawable.star_on)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_star -> {
                if (dbmanager.doesNotExist(title)) {
                    item.icon = resources.getDrawable(R.drawable.star_on)
                    dbmanager.insert(title, DualModel.RECORD_RESPONSE)
                } else {
                    item.icon = resources.getDrawable(R.drawable.star_off)
                    dbmanager.delete(title)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
