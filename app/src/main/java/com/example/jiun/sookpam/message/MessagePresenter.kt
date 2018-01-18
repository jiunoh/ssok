package com.example.jiun.sookpam.message

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.widget.ProgressBar
import com.example.jiun.sookpam.CategoryDBManager
import com.example.jiun.sookpam.R
import com.gun0912.tedpermission.PermissionListener
import io.realm.Realm

class MessagePresenter(
        private val context: Context,
        private val messagePermissionView: MessageContract.View,
        private val progressbar: ProgressBar) : MessageContract.Presenter {
    private lateinit var smsReader: SmsReader
    private lateinit var mmsReader: MmsReader
    private lateinit var categoryManager : CategoryDBManager

    init {
        messagePermissionView.presenter = this
    }

    override fun start() {
        performTaskOrFinishByPermission()
    }

    override fun performTaskOrFinishByPermission() {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                MessageAsyncTask().execute()
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
                messagePermissionView.finishActivity()
            }
        }
        messagePermissionView.showPermissionMessage(permissionListener)
    }

    override fun cancelMessageAsyncTask() {
        MessageAsyncTask().cancel(true)
    }

    override fun readAndSaveMessageList() {
        smsReader.gatherMessages(context)
        mmsReader.gatherMessages(context)
    }

    @SuppressLint("StaticFieldLeak")
    inner class MessageAsyncTask : AsyncTask<Unit, Unit, Unit>() {
        override fun onPreExecute() {
            super.onPreExecute()
            messagePermissionView
                    .showToastMessage(context.getString(R.string.start_message_synchronization))
            progressbar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg p0: Unit?) {
            var realm: Realm? = null
            publishProgress()
            Thread.sleep(2000)
            try {
                realm = Realm.getDefaultInstance()
                smsReader = SmsReader(realm)
                mmsReader = MmsReader(realm)
                categoryManager = CategoryDBManager(realm)
                readAndSaveMessageList()
                categoryManager.categorizeMessages(context)
            } finally {
                if (realm != null) {
                    realm.close()
                }
            }
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            messagePermissionView
                    .showToastMessage(context.getString(R.string.end_message_synchronization))
            progressbar.visibility = View.INVISIBLE
        }
    }
}