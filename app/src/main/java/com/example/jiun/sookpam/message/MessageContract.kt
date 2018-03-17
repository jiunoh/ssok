package com.example.jiun.sookpam.message

import com.example.jiun.sookpam.*
import com.gun0912.tedpermission.PermissionListener

interface MessageContract {
    interface View : BaseView<Presenter> {
        fun showPermissionMessage(permissionListener: PermissionListener)
    }

    interface Presenter : BasePresenter {
        fun readAndSaveMessageList()

        fun performTaskOrFinishByPermission()

        fun cancelMessageAsyncTask()
    }
}