package com.example.jiun.sookpam

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jiun.sookpam.clip.ClipItemRecyclerViewAdapter
import com.example.jiun.sookpam.message.ContentActivity
import com.example.jiun.sookpam.message.ContentItem
import com.example.jiun.sookpam.model.vo.RecordVO
import com.example.jiun.sookpam.searchable.DualModel
import com.example.jiun.sookpam.server.RecordResponse
import com.example.jiun.sookpam.web.WebContentActivity
import kotlinx.android.synthetic.main.fragment_my_clip.view.*
import java.util.ArrayList

class MyClipFragment : Fragment() {
    private var responseList: ArrayList<DualModel>? = null
    private var adapter: ClipItemRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        responseList = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_my_clip, container, false)
        // Set the adapter
        adapter = ClipItemRecyclerViewAdapter(responseList)
        view.recylerView.adapter = adapter
        view.recylerView.addOnItemTouchListener(RecyclerItemClickListener(context,
                RecyclerItemClickListener.OnItemClickListener { view, position ->
                    val data = responseList!!.get(position)
                    showMessageBody(data)
                }))
        adapter!!.loadData()
        return view
    }

    private fun showMessageBody(data: DualModel) {
        val bundle = Bundle()
        var intent: Intent? = null
        if (data is RecordVO) {
            var contentItem: ContentItem = ContentItem()
            contentItem.category = data.category
            contentItem.division = data.division
            contentItem.body = data.message!!.body
            contentItem.phone = data.message!!.phoneNumber
            intent = Intent(context, ContentActivity::class.java)
            bundle.putSerializable("OBJECT", contentItem)

        } else {
            intent = Intent(context, WebContentActivity::class.java)
            bundle.putSerializable("record", data as RecordResponse)
        }
        intent!!.putExtras(bundle)
        startActivity(intent)
    }
}
