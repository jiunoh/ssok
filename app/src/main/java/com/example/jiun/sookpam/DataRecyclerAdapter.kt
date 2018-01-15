package com.example.jiun.sookpam

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

data class DataItem(var title:String, var body:String)

class DataViewHolder (v: View) : RecyclerView.ViewHolder(v) {
    var title: View = v
    var body: View? = null
}

class DataRecyclerAdapter(private val dataItems:ArrayList<DataItem>):RecyclerView.Adapter<DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val inflatedView = parent.inflate(R.layout.data_recycler_item, false)
        return DataViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return dataItems.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}