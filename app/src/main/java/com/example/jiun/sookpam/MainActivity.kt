package com.example.jiun.sookpam

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*

import com.example.jiun.sookpam.model.mms.MmsReader
import com.example.jiun.sookpam.model.sms.SmsReader
import com.gun0912.tedpermission.PermissionListener

import com.gun0912.tedpermission.TedPermission

class MainActivity : AppCompatActivity() {
    private lateinit var smsReader: SmsReader
    private lateinit var mmsReader: MmsReader
    private lateinit var categoryManager: CategoryManager
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MainListviewAdapter()
        val listView = findViewById<View>(R.id.main_listView) as ListView
        listView.adapter = adapter
        initialize()
        checkMessagePermission()
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.arrow), "demo category")
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.arrow), "demo category")
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.arrow), "demo category")
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.arrow), "demo category")

        listView.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id -> go() }
    }

    private fun checkMessagePermission() {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                readMessageList()
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
                finish()
            }
        }

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleTitle(getString(R.string.read_sms_request_title))
                .setRationaleMessage(getString(R.string.read_sms_request_detail))
                .setDeniedTitle(getString(R.string.denied_read_sms_title))
                .setDeniedMessage(getString(R.string.denied_read_sms_detail))
                .setGotoSettingButtonText(getString(R.string.move_setting))
                .setPermissions(android.Manifest.permission.READ_SMS)
                .check()
    }

    private fun initialize() {
        //Realm.init(this)
        smsReader = SmsReader()
        mmsReader = MmsReader()
        toolbar = main_toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        categoryManager = CategoryManager()
    }

    private fun go() {
        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
    }

    private fun readMessageList() {
        smsReader.setSms(this)
        mmsReader.setMms(this)
        categoryManager.categorizeMessages(applicationContext)
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

   fun callMessageBYCategoryNeeded() {
        var response = categoryManager.getMessageByCategory("소프트웨어학부")
        for(record in response)
            Log.v("ONCLICK",record)
    }

}
