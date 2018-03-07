package com.example.jiun.sookpam.message

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.clip.ClipDBManager
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_content.*


class ContentActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var dbmanager: ClipDBManager
    private var title = "test"
    private var category : String? = ""
    private var division : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        val intent = intent
        val record: ContentItem = intent.getSerializableExtra("OBJECT") as ContentItem
        division = record.division
        category = record.category
        setToolbar(category + " > " + division)
        val body = record.body
        title = body!!.split("\n")[0]
        content_view.text = body
        val info = record.phone

        info_view.setText(division + "\t" + info)
    }

    private fun setToolbar(category: String) {
        toolbar = content_toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitle(category)
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary))
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                finish()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_content, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_star -> {
                dbmanager = ClipDBManager(Realm.getDefaultInstance());
                if (dbmanager.doesNotExist(title)) {
                    item.icon = resources.getDrawable(R.drawable.star_on)
                    dbmanager.insert(title, "메세지-"+category+"-"+division)
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
