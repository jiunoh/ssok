package com.example.jiun.sookpam

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class WebCommonTopicFragment : Fragment() {
    private lateinit var webCommonTopicRecyclerView: RecyclerView
    var topics: List<CommonTopic>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_web_common_topic, container, false)

        return inflater.inflate(R.layout.fragment_web_common_topic, container, false)
    }
}
