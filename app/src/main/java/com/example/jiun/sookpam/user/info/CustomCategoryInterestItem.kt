package com.example.jiun.sookpam.user.info

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.category_interest_item.view.*

class CustomCategoryInterestItem : LinearLayout {
    lateinit var categoryItemStatusTextView: TextView
    lateinit var categoryItemNameTextView: TextView
    lateinit var typedArray: TypedArray

    constructor(context: Context) : this(context, null) {
        initView()
    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {
        initView()
        getAttributeSet(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(context, attributeSet, defStyle) {
        initView()
        getAttributeSet(attributeSet, defStyle)
    }


    private fun initView() {
        val inflaterService = Context.LAYOUT_INFLATER_SERVICE
        val layoutInflater = context.getSystemService(inflaterService) as LayoutInflater
        layoutInflater.inflate(R.layout.category_interest_item, this)
        categoryItemStatusTextView = category_item_status_txt
        categoryItemNameTextView = category_item_name_txt
        categoryItemNameTextView.setOnClickListener {
            val categoryName = categoryItemNameTextView.text.toString()
            val categoryStatus = SharedPreferenceUtil.get(context, categoryName, 0)
            when (categoryStatus) {
                0 -> SharedPreferenceUtil.set(context, categoryName, 1)
                1 -> SharedPreferenceUtil.set(context, categoryName, 2)
                else -> SharedPreferenceUtil.set(context, categoryName, 0)
            }
            setStatusTextView()
        }
    }

    private fun getAttributeSet(attributeSet: AttributeSet?, defStyle: Int? = null) {
        typedArray = if (defStyle == null) {
            context.obtainStyledAttributes(attributeSet, R.styleable.CategoryInterest)
        } else {
            context.obtainStyledAttributes(attributeSet, R.styleable.CategoryInterest, defStyle, 0)
        }
        setTypeArray()
        typedArray.recycle()
    }

    private fun setTypeArray() {
        val categoryName = typedArray.getResourceId(R.styleable.CategoryInterest_name_txt, categoryItemNameTextView.id)
        categoryItemNameTextView.setText(categoryName)

        setStatusTextView()
    }

    private fun setStatusTextView() {
        val name = categoryItemNameTextView.text.toString()
        val status = SharedPreferenceUtil.get(context, name, 0)
        val statusColor = when (status) {
            0 -> R.color.colorPrimaryDark
            1 -> R.color.colorCategoryAccent
            else -> R.color.colorCategoryGray
        }
        categoryItemStatusTextView.setBackgroundResource(statusColor)
        val statusName = when (status) {
            0 -> R.string.category_status_normal
            1 -> R.string.category_status_interest
            else -> R.string.category_status_uninterest
        }
        categoryItemStatusTextView.setText(statusName)
    }
}