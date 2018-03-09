package com.example.jiun.sookpam

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class SimpleViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    private var pageEnabled: Boolean = false

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return if (pageEnabled) {
            super.onInterceptTouchEvent(ev)
        } else {
            if (ev.actionMasked == MotionEvent.ACTION_MOVE) {
            } else {
                if (super.onInterceptTouchEvent(ev)) {
                    super.onTouchEvent(ev)
                }
            }
            false
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (pageEnabled) {
            super.onTouchEvent(ev)
        } else {
            ev.actionMasked != MotionEvent.ACTION_MOVE && super.onTouchEvent(ev)
        }
    }

    fun setPagingEnabled(enabled: Boolean) {
        pageEnabled = enabled
    }
}
