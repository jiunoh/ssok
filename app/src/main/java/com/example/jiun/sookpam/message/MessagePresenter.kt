package com.example.jiun.sookpam.message

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.view.View
import android.widget.ProgressBar
import com.example.jiun.sookpam.*
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.model.RecordDBManager
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import com.gun0912.tedpermission.PermissionListener
import io.realm.Realm

class MessagePresenter(
        private val context: Context,
        private val messagePermissionView: MessageContract.View,
        private val loadingDialog: LoadingDialog,
        private val progressBar: ProgressBar) : MessageContract.Presenter {
    private lateinit var smsReader: SmsReader
    private lateinit var mmsReader: MmsReader
    private lateinit var recordManager: RecordDBManager
    private var isFirstLoading: Boolean = false

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
                CustomToast.showLastToast(context, context.getString(R.string.message_permission_denied))
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
            isFirstLoading = SharedPreferenceUtil.get(context, IS_FIRST_LOADING, true)
            if (isFirstLoading) {
                CustomToast.showLastToast(context, "메세지를 목록을 가져옵니다.\n첫 로딩 시 시간이 다소 소요될 수 있습니다.")
                loadingDialog.show()
            } else {
                progressBar.visibility = View.VISIBLE
            }

        }

        override fun doInBackground(vararg p0: Unit?) {
            var realm: Realm? = null
            publishProgress()
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
            progressBar.visibility = View.GONE
            if (isFirstLoading) {
                loadingDialog.dismiss()
                CustomToast.showLastToast(context, context.getString(R.string.end_synchronization))
                SharedPreferenceUtil.set(context, IS_FIRST_LOADING, false)
                MessageBaseFragment.messageViewPagerAdapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        const val IS_FIRST_LOADING = "isFirstLoading"
    }
}