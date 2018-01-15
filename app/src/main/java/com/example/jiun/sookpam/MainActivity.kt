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
import com.gun0912.tedpermission.PermissionListener

import io.realm.Realm
import com.gun0912.tedpermission.TedPermission

class MainActivity : AppCompatActivity() {
    private lateinit var smsReader: SmsReader
    private lateinit var mmsReader: MmsReader
    private lateinit var categoryParser : CategoryParser
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
        checkMessagePermission()
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
        categoryParser = CategoryParser()
    }

    private fun readMessageList() {
        smsReader.setSms(this)
        mmsReader.setMms(this)
        categoryParser.categorizeMessages(applicationContext)
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
