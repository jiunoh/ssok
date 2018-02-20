package com.example.jiun.sookpam.user.major

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.jiun.sookpam.CustomLinearLayoutManager
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.RecyclerItemClickListener
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter
import com.github.aakira.expandablelayout.ExpandableLinearLayout
import com.github.aakira.expandablelayout.Utils
import kotlinx.android.synthetic.main.college_recycler_item.view.*
import kotlinx.android.synthetic.main.major_recycler_item.view.*

class CollegeRecyclerAdapter(val data: List<MajorItemModel>) : RecyclerView.Adapter<CollegeRecyclerAdapter.ViewHolder>() {
    lateinit var context: Context
    lateinit var layoutManager: RecyclerView.LayoutManager
    var expandState: SparseBooleanArray = SparseBooleanArray()

    init {
        var i = 0
        while (i < data.size) {
            expandState.append(i++, false)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        context = parent!!.context
        layoutManager = CustomLinearLayoutManager(context)
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.college_recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, @SuppressLint("RecyclerView") position: Int) {
        val item = data[position]
        holder!!.setIsRecyclable(false)
        holder.collegeTextView.text = item.college
        holder.itemView.setBackgroundColor(Color.WHITE)
        holder.expandableLayout.setInRecyclerView(true)
        holder.expandableLayout.setBackgroundColor(Color.WHITE)
        holder.expandableLayout.setInterpolator(item.interpolator)
        holder.expandableLayout.isExpanded = expandState[position]
        holder.expandableLayout.setListener(object : ExpandableLayoutListenerAdapter() {
            override fun onPreOpen() {
                createRotateAnimator(holder.collegeRelativeLayout, 0f, 180f).start()
                expandState.put(position, true)
            }

            override fun onPreClose() {
                createRotateAnimator(holder.collegeRelativeLayout, 180f, 0f).start()
                expandState.put(position, false)
            }
        })

        holder.collegeRelativeLayout.rotation = if (expandState.get(position)) 180f else 0f
        holder.collegeRelativeLayout.setOnClickListener({ onClickButton(holder.expandableLayout) })
        holder.collegeTextView.setOnClickListener({ onClickButton(holder.expandableLayout) })

        holder.majorRecyclerView.layoutManager = layoutManager
        createMajorList(holder, item.college)
    }

    private fun createMajorList(holder: ViewHolder, college: String) {
        val arrayList: ArrayList<String> = ArrayList()
        when (college) {
            context.getString(R.string.college_liberalarts) -> {
                arrayList.add("한국어문학부")
                arrayList.add("역사문화학과")
                arrayList.add("프랑스언어문화학과")
                arrayList.add("중어중문학부")
                arrayList.add("독일언어문화학과")
                arrayList.add("일본학과")
                arrayList.add("문헌정보학과")
                arrayList.add("문화관광학부")
                arrayList.add("교육학부")
            }
            context.getString(R.string.college_science) -> {
                arrayList.add("화학과")
                arrayList.add("생명시스템학부")
                arrayList.add("수학과")
                arrayList.add("통계학과")
                arrayList.add("체육교육과")
                arrayList.add("무용과")
            }
            context.getString(R.string.college_engineering) -> {
                arrayList.add("화공생명공학부")
                arrayList.add("ICT융합공학부")
                arrayList.add("소프트웨어학부")
                arrayList.add("기계시스템학부")
                arrayList.add("기초공학부")
            }
            context.getString(R.string.college_human_ecology) -> {
                arrayList.add("가족자원경영학과")
                arrayList.add("아동복지학부")
                arrayList.add("의류학과")
                arrayList.add("식품영양학과")
            }
            context.getString(R.string.college_social_sciences) -> {
                arrayList.add("정치외교학과")
                arrayList.add("행정학과")
                arrayList.add("홍보광고학과")
                arrayList.add("소비자경제학과")
                arrayList.add("사회심리학과")
            }
            context.getString(R.string.college_law) -> {
                arrayList.add("법학부")
            }
            context.getString(R.string.college_economics_business_administration) -> {
                arrayList.add("경제학부")
                arrayList.add("경영학부")
            }
            context.getString(R.string.college_music) -> {
                arrayList.add("피아노과")
                arrayList.add("관현악과")
                arrayList.add("성악과")
                arrayList.add("작곡과")
            }
            context.getString(R.string.college_pharmacy) -> {
                arrayList.add("약학부")
            }
            context.getString(R.string.college_fine_art) -> {
                arrayList.add("시각영상디자인과")
                arrayList.add("산업디자인과")
                arrayList.add("환경디자인과")
                arrayList.add("공예과")
                arrayList.add("회화과")
            }
            context.getString(R.string.school_global_service) -> {
                arrayList.add("글로벌협력전공")
                arrayList.add("앙트러프러너십전공")
            }
            context.getString(R.string.school_english) -> {
                arrayList.add("영어영문학전공")
                arrayList.add("테슬(TESL)전공")
            }
            context.getString(R.string.school_communication_media) -> {
                arrayList.add("미디어학부")
            }
        }
        holder.majorRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(context,
                RecyclerItemClickListener.OnItemClickListener { view, position ->
                    val isCurrentItemChecked = SharedPreferenceUtil.get(context, view.major_txt.toString(), MajorRecyclerAdapter.UNCHECKED)
                    if (isCurrentItemChecked) {
                        SharedPreferenceUtil.set(context, view.major_txt.toString(), MajorRecyclerAdapter.UNCHECKED)
                        view.major_check_img.setImageResource(R.drawable.ic_check_white)
                    } else {
                        SharedPreferenceUtil.set(context, view.major_txt.toString(), MajorRecyclerAdapter.CHECKED)
                        view.major_check_img.setImageResource(R.drawable.ic_check_pink)
                    }
                }))
        holder.majorRecyclerView.adapter = MajorRecyclerAdapter(arrayList)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun onClickButton(expandableLinearLayout: ExpandableLinearLayout) {
        expandableLinearLayout.toggle()
    }

    fun createRotateAnimator(target: View, from: Float, to: Float): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(target, "rotation", from, to)
        animator.duration = 300
        animator.interpolator = Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR)
        return animator
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var collegeTextView: TextView = v.major_college_txt
        var majorRecyclerView: RecyclerView = v.major_recycler_view
        var collegeRelativeLayout: RelativeLayout = v.major_college_relative
        var expandableLayout: ExpandableLinearLayout = v.major_expandable_layout
    }
}
