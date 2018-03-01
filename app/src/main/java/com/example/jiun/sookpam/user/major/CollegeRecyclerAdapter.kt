package com.example.jiun.sookpam.user.major

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.*
import android.widget.*
import com.example.jiun.sookpam.*
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import com.github.aakira.expandablelayout.*
import com.github.aakira.expandablelayout.Utils
import kotlinx.android.synthetic.main.college_recycler_item.view.*
import kotlinx.android.synthetic.main.major_recycler_item.view.*
import kotlin.collections.ArrayList

class CollegeRecyclerAdapter(val data: List<MajorItemModel>) : RecyclerView.Adapter<CollegeRecyclerAdapter.ViewHolder>() {
    lateinit var context: Context
    private lateinit var layoutManager: RecyclerView.LayoutManager
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
        val view = LayoutInflater.from(context).inflate(R.layout.college_recycler_item, parent, false)
        return ViewHolder(view)
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
        val majorsInCollege = getMajorsFrom(item.college)
        holder.majorRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(context,
                RecyclerItemClickListener.OnItemClickListener { view, _ ->
                    setCheckStatus(view, view.major_txt.text.toString())
                }))
        holder.majorRecyclerView.adapter = MajorRecyclerAdapter(majorsInCollege)
    }

    private fun getMajorsFrom(college: String): ArrayList<String> {
        when (college) {
            context.getString(R.string.college_liberalarts) -> {
                return MajorList.liberalarts
            }
            context.getString(R.string.college_science) -> {
                return MajorList.science
            }
            context.getString(R.string.college_engineering) -> {
                return MajorList.engineering
            }
            context.getString(R.string.college_human_ecology) -> {
                return MajorList.humanEcology
            }
            context.getString(R.string.college_social_sciences) -> {
                return MajorList.socialSciences
            }
            context.getString(R.string.college_law) -> {
                return MajorList.law
            }
            context.getString(R.string.college_economics_business_administration) -> {
                return MajorList.economics
            }
            context.getString(R.string.college_music) -> {
                return MajorList.music
            }
            context.getString(R.string.college_pharmacy) -> {
                return MajorList.pharmacy
            }
            context.getString(R.string.college_fine_art) -> {
                return MajorList.fineArt
            }
            context.getString(R.string.school_global_service) -> {
                return MajorList.global
            }
            context.getString(R.string.school_english) -> {
                return MajorList.english
            }
            context.getString(R.string.school_communication_media) -> {
                return MajorList.media
            }
            else -> return ArrayList()
        }
    }

    private fun setCheckStatus(view: View, majorName: String) {
        val isCurrentItemChecked = SharedPreferenceUtil.get(context, majorName, MajorRecyclerAdapter.UNCHECKED)
        if (isCurrentItemChecked) {
            SharedPreferenceUtil.set(context, majorName, MajorRecyclerAdapter.UNCHECKED)
            view.major_check_img.setImageResource(R.drawable.ic_check_white)
        } else {
            SharedPreferenceUtil.set(context, majorName, MajorRecyclerAdapter.CHECKED)
            view.major_check_img.setImageResource(R.drawable.ic_check_pink)
        }
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
