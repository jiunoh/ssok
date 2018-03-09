package com.example.jiun.sookpam

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jiun.sookpam.web.common.WebCommonRecyclerActivity
import kotlinx.android.synthetic.main.fragment_web_common_topic.view.*

class WebCommonTopicFragment : Fragment() {
    private lateinit var webCommonTopicRecyclerView: RecyclerView
    private lateinit var topics: List<CommonTopic>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_web_common_topic, container, false)
        initialize(view)
        return view
    }

    private fun initialize(view: View) {
        topics = CommonTopicAdapter.getInterestOrNormalTopics(context!!)
        webCommonTopicRecyclerView = view.web_common_topic_recycler
        webCommonTopicRecyclerView.adapter = CommonTopicRecyclerAdapter(topics)
        webCommonTopicRecyclerView.layoutManager = LinearLayoutManager(context)
        webCommonTopicRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(context, RecyclerItemClickListener.OnItemClickListener { _, position ->
            if(topics[position].topicTitle!="취업"){
                val intent = Intent(context, WebCommonRecyclerActivity::class.java)
                intent.putExtra("category", "공통")
                intent.putExtra("division", topics[position].topicTitle)
                startActivity(intent)
            }
        }))
    }
}
