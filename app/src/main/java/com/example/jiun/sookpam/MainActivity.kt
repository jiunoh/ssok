package com.example.jiun.sookpam

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

import com.example.jiun.sookpam.model.mms.MmsReader
import com.example.jiun.sookpam.model.sms.SmsReader

import io.realm.Realm

class MainActivity : AppCompatActivity() {
    private lateinit var smsReader: SmsReader
    private lateinit var mmsReader: MmsReader
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
        readMessageList()
    }

    private fun initialize() {
        Realm.init(this)
        smsReader = SmsReader()
        mmsReader = MmsReader()
        toolbar = main_toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
    }

    private fun readMessageList() {
        smsReader.setSms(this)
        mmsReader.setMms(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_synchronization -> {
                readMessageList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
