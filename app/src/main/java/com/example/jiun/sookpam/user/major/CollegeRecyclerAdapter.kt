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
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter
import com.github.aakira.expandablelayout.ExpandableLinearLayout
import com.github.aakira.expandablelayout.Utils
import kotlinx.android.synthetic.main.college_recycler_item.view.*

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
        holder.majorRecyclerView.layoutManager = layoutManager
        val arrayList = ArrayList<String>()
        arrayList.add("1")
        arrayList.add("2")
        holder.majorRecyclerView.adapter = MajorRecyclerAdapter(arrayList)
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
