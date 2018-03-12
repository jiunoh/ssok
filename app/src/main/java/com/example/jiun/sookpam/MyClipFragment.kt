package com.example.jiun.sookpam

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jiun.sookpam.clip.ClipDBManager
import com.example.jiun.sookpam.clip.ClipItemRecyclerViewAdapter
import com.example.jiun.sookpam.message.ContentActivity
import com.example.jiun.sookpam.message.ContentItem
import com.example.jiun.sookpam.model.vo.RecordVO
import com.example.jiun.sookpam.model.DualModel
import com.example.jiun.sookpam.model.RecordDBManager
import com.example.jiun.sookpam.model.vo.DualVO
import com.example.jiun.sookpam.server.ApiUtils
import com.example.jiun.sookpam.server.RecordResponse
import com.example.jiun.sookpam.web.WebContentActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_my_clip.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MyClipFragment : Fragment() {
    private lateinit var modelList: List<DualModel>
    private var adapter: ClipItemRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        modelList = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_my_clip, container, false)
        view.recylerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        view.recylerView.addOnItemTouchListener(RecyclerItemClickListener(context,
                RecyclerItemClickListener.OnItemClickListener { view, position ->
                    val data = modelList!!.get(position)
                    showMessageBody(data)
                }))
        return view
    }

    override fun onResume() {
        super.onResume()
        search()
    }

    fun search() {
        modelList = ArrayList<DualModel>()
        adapter = ClipItemRecyclerViewAdapter(modelList)
        view!!.recylerView.adapter = adapter
        val dbManager = ClipDBManager(Realm.getDefaultInstance())
        val voList = dbManager.select()
        for( vo in voList)
            getModelBy(vo)
        modelList = adapter!!.update()
    }

    private fun getModelBy(record: DualVO) {
        if (record.type == DualModel.RECORD_VO) {
            val recordManager = RecordDBManager(Realm.getDefaultInstance())
            val recordVos = recordManager.contains(record.title) as List<RecordVO>
            adapter!!.add(recordVos)
        } else if(record.type == DualModel.RECORD_RESPONSE){
            try {
                searchInWeb(record.title)
            } catch (exception : IndexOutOfBoundsException ){
                Log.v("getModelBy","데이터가 없습니다.");
            }
        }
    }

    private fun searchInWeb(charText: String){
        val service = ApiUtils.getSearchableService()
        val query = charText.replace("\\s+".toRegex(), "-")
        var tempList :  ArrayList<RecordResponse> = ArrayList()
        service.getItems(query).enqueue(object : Callback<List<RecordResponse>> {
            override fun onResponse(call: Call<List<RecordResponse>>, response: Response<List<RecordResponse>>){
                if (!response.isSuccessful) {
                    Log.v("response", " disconnected")
                    return
                }
                Log.v("response.body>", response.body().toString())
                val records = response.body()
                adapter!!.add(records)
            }

            override fun onFailure(call: Call<List<RecordResponse>>, t: Throwable) {
                Log.v("onFailure:", "onFailure")
            }
        })

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
