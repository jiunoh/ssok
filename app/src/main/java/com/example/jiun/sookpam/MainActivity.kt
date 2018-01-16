package com.example.jiun.sookpam

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast
import com.example.jiun.sookpam.message.MessageContract
import com.example.jiun.sookpam.message.MessagePresenter
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_main.*

import io.realm.Realm

class MainActivity : AppCompatActivity(), MessageContract.View {
    override lateinit var presenter: MessageContract.Presenter
    private lateinit var toolbar: Toolbar
    private lateinit var progressbar: ProgressBar

    override fun showPermissionMessage(permissionListener: PermissionListener) {
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

    override fun showToastMessage(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun finishActivity() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
        presenter.start()
    }

    override fun onPause() {
        super.onPause()
        presenter.cancelMessageAsyncTask()
    }

    private fun initialize() {
        Realm.init(this)
        progressbar = main_message_prograssbar
        presenter = MessagePresenter(this, this, progressbar)
        setToolbar()
    }

    private fun setToolbar() {
        toolbar = main_toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_synchronization -> {
                presenter.start()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
