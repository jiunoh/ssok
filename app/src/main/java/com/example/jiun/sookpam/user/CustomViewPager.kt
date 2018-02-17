package com.example.jiun.sookpam.user

import android.content.Context
import android.support.v4.view.MotionEventCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class CustomViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    var pageenabled: Boolean = false

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (pageenabled) {
            super.onInterceptTouchEvent(ev)
        } else {
            if (MotionEventCompat.getActionMasked(ev) == MotionEvent.ACTION_MOVE) {
            } else {
                if (super.onInterceptTouchEvent(ev)) {
                    super.onTouchEvent(ev)
                }
            }
            false
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (pageenabled) {
            super.onTouchEvent(ev)
        } else {
            MotionEventCompat.getActionMasked(ev) != MotionEvent.ACTION_MOVE && super.onTouchEvent(ev)
        }
    }

    fun setPagingEnabled(enabled: Boolean) {
        pageenabled = enabled
    }
}
