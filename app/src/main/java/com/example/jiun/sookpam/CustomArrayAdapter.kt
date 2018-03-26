package com.example.jiun.sookpam

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CustomArrayAdapter : ArrayAdapter<String> {
    private var objects: List<String>
    private var customContext: Context

    constructor(context: Context, resourceId: Int, objects: Array<String>) : super(context, resourceId, objects) {
        this.objects = objects.toList()
        this.customContext = context
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getCustomView(position, convertView, parent)
    }

    private fun getCustomView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = customContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val row = inflater.inflate(R.layout.spinner_item, parent, false)
        val label = row.findViewById<TextView>(R.id.spinner_item_txt)
        label.text = objects[position]
        return row
    }
}