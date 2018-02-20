package com.example.jiun.sookpam.message

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import com.example.jiun.sookpam.R
import android.content.Intent
import android.view.MenuItem
import android.view.View
import com.example.jiun.sookpam.R.id.action_star
import com.example.jiun.sookpam.clip.ClipDBManager
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_content.*


class ContentActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var dbmanager : ClipDBManager
    private var title = "test"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        setToolbar()
    }

    private fun setToolbar() {
        toolbar = content_toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitle("Sookpam")
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
                dbmanager =   ClipDBManager(Realm.getDefaultInstance());
                if (dbmanager.doesNotExist(title)) {
                    item.setIcon(getResources().getDrawable(R.drawable.star_on))
                    dbmanager.insert(title)
                }
                else {
                    item.setIcon(getResources().getDrawable(R.drawable.star_off))
                    dbmanager.delete(title)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
