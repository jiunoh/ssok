package com.example.jiun.ssok.util

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

@GlideModule
class AppGlideModule : AppGlideModule(){
    fun setImageByGlide(imageView:ImageView, imageResource: Int, context:Context) {
        Glide.with(context).asBitmap().load(imageResource)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        imageView.setImageBitmap(resource)
                    }
                })
    }
}