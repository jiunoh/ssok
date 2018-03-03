package com.example.jiun.sookpam

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation

class UIAnimation {
    companion object {
        fun setRotateAnimation(image: View): RotateAnimation {
            var currentRotation = 0f
            currentRotation %= 360
            val fromRotation = currentRotation
            currentRotation += 360
            val toRotation = currentRotation
            val rotateAnim = RotateAnimation(
                    fromRotation, toRotation, image.width.toFloat() / 2, image.height.toFloat() / 2)
            rotateAnim.duration = 1000
            rotateAnim.fillAfter = true
            return rotateAnim
        }


        fun setLoadingRecyclerViewAnimation(context: Context?, recyclerView: RecyclerView) {
            val controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
            recyclerView.layoutAnimation = controller
            recyclerView.adapter.notifyDataSetChanged()
            recyclerView.scheduleLayoutAnimation()
        }
    }
}