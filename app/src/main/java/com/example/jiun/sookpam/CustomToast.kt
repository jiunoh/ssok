package com.example.jiun.sookpam

import android.content.Context
import android.widget.Toast

class CustomToast {
    companion object {
        private var toast: Toast? = null
        fun showLastToast(context: Context, message: String) {
            if (toast != null) {
                toast!!.cancel()
            }
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            toast!!.show()
        }
    }
}