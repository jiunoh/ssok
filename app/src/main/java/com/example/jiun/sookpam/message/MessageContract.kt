package com.example.jiun.sookpam.message

import com.example.jiun.sookpam.BasePresenter
import com.example.jiun.sookpam.BaseView
import com.gun0912.tedpermission.PermissionListener

interface MessageContract {
    interface View : BaseView<Presenter> {
        fun showPermissionMessage(permissionListener: PermissionListener)

        fun finishActivity()
    }

    interface Presenter : BasePresenter {
        fun readMessageList()

        fun checkPermission()

        fun cancelMessageAsyncTask()
    }
}