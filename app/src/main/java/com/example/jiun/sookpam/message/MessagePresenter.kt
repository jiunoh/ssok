package com.example.jiun.sookpam.message

import android.content.Context
import com.example.jiun.sookpam.message.mms.MmsReader
import com.example.jiun.sookpam.message.sms.SmsReader
import com.gun0912.tedpermission.PermissionListener

class MessagePresenter(
        private val context: Context,
        private val messagePermissionView: MessageContract.View) : MessageContract.Presenter {
    private val smsReader: SmsReader = SmsReader()
    private val mmsReader: MmsReader = MmsReader()

    init {
        messagePermissionView.presenter = this
    }

    override fun start() {
        checkPermission()
    }

    override fun readMessageList() {
        smsReader.gatherMessages(context)
        mmsReader.gatherMessages(context)
    }

    override fun checkPermission() {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                readMessageList()
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
                messagePermissionView.finishActivity()
            }
        }

        messagePermissionView.showPermissionMessage(permissionListener)
    }
}