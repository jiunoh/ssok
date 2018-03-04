package com.example.jiun.sookpam

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import kotlinx.android.synthetic.main.loading_dialog.*

class LoadingDialog(context: Context) : Dialog(context) {
    private lateinit var imageView:ImageView
    private lateinit var animation:Animation

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        setCanceledOnTouchOutside(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_dialog)
        imageView = loading_dialog_image_view
    }

    override fun show() {
        super.show()
        animation = AnimationUtils.loadAnimation(context, R.anim.loading)
        imageView.animation = animation
    }
}
