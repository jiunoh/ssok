package com.example.jiun.sookpam

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.jiun.sookpam.clip.ClipDBManager
import com.example.jiun.sookpam.clip.ClipItemRecyclerViewAdapter
import com.example.jiun.sookpam.model.vo.RecordVO
import com.example.jiun.sookpam.model.DualModel
import com.example.jiun.sookpam.model.RecordDBManager
import com.example.jiun.sookpam.model.vo.DualVO
import com.example.jiun.sookpam.server.ApiUtils
import com.example.jiun.sookpam.server.RecordResponse
import com.example.jiun.sookpam.util.MsgContentGenerator
import com.example.jiun.sookpam.web.WebContentActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_my_clip.*
import kotlinx.android.synthetic.main.fragment_my_clip.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class MyClipFragment : Fragment() {
    private lateinit var modelList: List<DualModel>
    private var adapter: ClipItemRecyclerViewAdapter? = null
    private lateinit var errorLinearLayout: LinearLayout
    private lateinit var errorImageView: ImageView
    private lateinit var errorTextView: TextView
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        modelList = ArrayList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_my_clip, container, false)
        recyclerView = view.recylerView
        recyclerView.visibility = View.VISIBLE
        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(context,
                RecyclerItemClickListener.OnItemClickListener { view, position ->
                    val data = modelList!!.get(position)
                    showMessageBody(data)
                }))
        errorLinearLayout = view.common_empty_linear
        errorLinearLayout.visibility = View.INVISIBLE
        errorImageView = view.common_error_img
        errorTextView = view.common_error_txt
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
        for (vo in voList)
            getModelBy(vo)
        modelList = adapter!!.modelList
        if (modelList.isEmpty())
            showNoData()
        else {
            errorLinearLayout.visibility = View.INVISIBLE
            view!!.recylerView.visibility = View.VISIBLE
        }
    }

    private fun getModelBy(record: DualVO) {
        if (record.type == DualModel.RECORD_VO) {
            val recordManager = RecordDBManager(Realm.getDefaultInstance())
            val recordVos = recordManager.contains(record.title) as List<RecordVO>
            adapter!!.add(recordVos)
        } else if (record.type == DualModel.RECORD_RESPONSE) {
            try {
                searchInWeb(record.title, record.date!!)
            } catch (exception: IndexOutOfBoundsException) {
                Log.v("getModelBy", "데이터가 없습니다.");
            }
        }
    }

    private fun searchInWeb(charText: String, date: String) {
        val service = ApiUtils.getSearchableService()
        val query = charText.replace("\\s+".toRegex(), "--")
        val request = query.replace("/".toRegex(),"__")
        service.getItems(request).enqueue(object : Callback<List<RecordResponse>> {
            override fun onResponse(call: Call<List<RecordResponse>>, response: Response<List<RecordResponse>>) {
                if (!response.isSuccessful) {
                    Log.v("response", " disconnected")
                    return
                }
                val records = response.body()
                records!!.forEach { record ->
                    if (record.date == date)
                        adapter!!.add(record)
                }
            }

            override fun onFailure(call: Call<List<RecordResponse>>, t: Throwable) {
                Log.v("onFailure:", "onFailure")
            }
        })
    }


    private fun showMessageBody(data: DualModel) {
        if (data is RecordVO) {
            MsgContentGenerator.showMessageBody(context, data)
        } else {
            val intent = Intent(context, WebContentActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable("record", data as RecordResponse)
            intent!!.putExtras(bundle)
            startActivity(intent)
        }
    }

    private fun showNoData() {
        recylerView.visibility = View.INVISIBLE
        errorLinearLayout.visibility = View.VISIBLE
    }

}
