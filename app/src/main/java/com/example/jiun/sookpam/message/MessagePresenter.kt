package com.example.jiun.sookpam.message

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.example.jiun.sookpam.LoadingDialog
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.model.RecordDBManager
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import com.gun0912.tedpermission.PermissionListener
import io.realm.Realm

class MessagePresenter(
        private val context: Context,
        private val messagePermissionView: MessageContract.View,
        private val loadingDialog: LoadingDialog) : MessageContract.Presenter {
    private lateinit var smsReader: SmsReader
    private lateinit var mmsReader: MmsReader
    private lateinit var recordManager: RecordDBManager

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
                messagePermissionView.showToastMessage(context.getString(R.string.message_permission_denied), Toast.LENGTH_SHORT)
            }
        }
        messagePermissionView.showPermissionMessage(permissionListener)
    }

    override fun cancelMessageAsyncTask() {
        loadingDialog.dismiss()
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
            val isFirstLoading = SharedPreferenceUtil.get(context, IS_FIRST_LOADING, true)
            if (isFirstLoading) {
                messagePermissionView.showToastMessage("메세지를 목록을 가져옵니다.\n첫 로딩 시 시간이 다소 소요될 수 있습니다.", Toast.LENGTH_LONG)
                SharedPreferenceUtil.set(context, IS_FIRST_LOADING, false)
            } else {
                messagePermissionView
                        .showToastMessage(context.getString(R.string.start_synchronization), Toast.LENGTH_SHORT)
            }
            loadingDialog.show()
        }

        override fun doInBackground(vararg p0: Unit?) {
            var realm: Realm? = null
            publishProgress()
            Thread.sleep(500)
            try {
                realm = Realm.getDefaultInstance()
                smsReader = SmsReader(realm)
                mmsReader = MmsReader(realm)
                recordManager = RecordDBManager(realm)
                readAndSaveMessageList()
                recordManager.categorizeMessages(context)
            } finally {
                if (realm != null) {
                    realm.close()
                }
            }
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            messagePermissionView
                    .showToastMessage(context.getString(R.string.end_synchronization), Toast.LENGTH_SHORT)
            loadingDialog.dismiss()
        }
    }

    companion object {
        const val IS_FIRST_LOADING = "isFirstLoading"
    }
}