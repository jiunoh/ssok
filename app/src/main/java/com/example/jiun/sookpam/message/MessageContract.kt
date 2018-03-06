package com.example.jiun.sookpam.message

import com.example.jiun.sookpam.BasePresenter
import com.example.jiun.sookpam.BaseView
import com.gun0912.tedpermission.PermissionListener

interface MessageContract {
    interface View : BaseView<Presenter> {
        fun showPermissionMessage(permissionListener: PermissionListener)

        fun showToastMessage(string: String, toastTime: Int)
    }

    interface Presenter : BasePresenter {
        fun readAndSaveMessageList()

        fun performTaskOrFinishByPermission()

        fun cancelMessageAsyncTask()
    }
}