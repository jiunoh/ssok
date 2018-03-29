package com.example.jiun.ssok.message.synchronization

import com.example.jiun.ssok.*
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