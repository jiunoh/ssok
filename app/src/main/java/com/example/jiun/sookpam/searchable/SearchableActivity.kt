package com.example.jiun.sookpam.searchable

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.appcompat.R.id.search_close_btn
import android.support.v7.appcompat.R.id.search_mag_icon
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.RecyclerItemClickListener
import com.example.jiun.sookpam.model.DualModel
import com.example.jiun.sookpam.model.vo.RecordVO
import com.example.jiun.sookpam.server.ApiUtils
import com.example.jiun.sookpam.server.RecordResponse
import com.example.jiun.sookpam.user.setting.SettingCategory
import com.example.jiun.sookpam.util.MsgContentGenerator
import com.example.jiun.sookpam.util.SharedPreferenceUtil
import com.example.jiun.sookpam.web.WebContentActivity
import kotlinx.android.synthetic.main.activity_searchable.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class SearchableActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var modelList: List<DualModel>
    private lateinit var editsearch: SearchView
    private lateinit var adapter: SearchableRecyclerAdapter
    private lateinit var errorLinearLayout: LinearLayout
    private lateinit var errorImageView: ImageView
    private lateinit var errorTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var keyword1: TextView
    private lateinit var keyword2: TextView
    private lateinit var keyword3: TextView
    private lateinit var keyword4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)
        modelList = ArrayList()
        setToolbar()
        setRecyclerView()
        setRestOfTheView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu!!.findItem(R.id.action_search)

        var similarKeywords = findViewById<LinearLayout>(R.id.search_keyword_layout)
        similarKeywords.visibility = View.VISIBLE
        keyword1 = findViewById(R.id.search_keyword_1)
        keyword2 = findViewById(R.id.search_keyword_2)
        keyword3 = findViewById(R.id.search_keyword_3)
        keyword4 = findViewById(R.id.search_keyword_4)

        var keywordRecomList: ArrayList<String>? = ArrayList()
        var key_janghak: Array<String> = arrayOf("대정", "홍산", "성음", "장학생", "자기계발", "재단법인")
        var key_haksa: Array<String> = arrayOf("사정", "소멸", "인증대체", "성적")
        var key_haengsa: Array<String> = arrayOf("특강", "축제", "눈송이", "초대", "만남")
        var key_mojip: Array<String> = arrayOf("단기", "행정", "마감", "월급", "조교", "급여", "출퇴근")
        var key_system: Array<String> = arrayOf("포털", "블루리본", "웹메일", "스노보드", "포털시스템", "커뮤니티")
        var key_gukje: Array<String> = arrayOf("협력", "교환학생", "교류", "해외")
        var key_chuiup: Array<String> = arrayOf("공기업", "토익", "재직", "선배", "강소기업", "업계", "서류", "직무", "자문", "멘토링")
        var key_haksaeng: Array<String> = arrayOf("청각장애", "속기", "안내견", "학생지원팀")

        var topics: ArrayList<String> = ArrayList()
        val random = Random()

        for (topic in SettingCategory.categories) {
            if (SharedPreferenceUtil.get(this, topic, 0) == 1) {
                topics.add(topic)
            }
        }

        for (topic in topics) {
            when (topic) {
                "장학" -> keywordRecomList?.add(key_janghak[random.nextInt(key_janghak.size)])
                "학사" -> keywordRecomList?.add(key_haksa[random.nextInt(key_haksa.size)])
                "행사" -> keywordRecomList?.add(key_haengsa[random.nextInt(key_haengsa.size)])
                "모집" -> keywordRecomList?.add(key_mojip[random.nextInt(key_mojip.size)])
                "시스템" -> keywordRecomList?.add(key_system[random.nextInt(key_system.size)])
                "국제" -> keywordRecomList?.add(key_gukje[random.nextInt(key_gukje.size)])
                "취업" -> keywordRecomList?.add(key_chuiup[random.nextInt(key_chuiup.size)])
                "학생" -> keywordRecomList?.add(key_haksaeng[random.nextInt(key_haksaeng.size)])
            }
        }

        var max = keywordRecomList?.size!!

        if (max < 4) {
            keywordRecomList.add("안내")
            max = 4
        }

        var indices: IntArray = intArrayOf(0, 0, 0, 0)
        var i = 0
        while (i < 4) {
            indices[i] = random.nextInt(max)
            for (j in 0 until i) {
                if (indices[i] === indices[j]) {
                    i--
                }
            }
            i++
        }

        keyword1.setText(keywordRecomList?.get(indices[0]))
        keyword2.setText(keywordRecomList?.get(indices[1]))
        keyword3.setText(keywordRecomList?.get(indices[2]))
        keyword4.setText(keywordRecomList?.get(indices[3]))

        editsearch = MenuItemCompat.getActionView(searchItem) as SearchView
        editsearch.setIconifiedByDefault(false)
        editsearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                similarKeywords.visibility = View.INVISIBLE
                search_recycler_view.visibility = View.VISIBLE
                errorLinearLayout.visibility = View.INVISIBLE
                search(query)
                editsearch.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        val icon = editsearch.findViewById(search_mag_icon) as ImageView
        icon.layoutParams = LinearLayout.LayoutParams(0, 0)
        icon.visibility = View.GONE
        setCloseEventListener()

        keyword1.setOnClickListener{
            var query = keyword1.text.toString()
            editsearch.setQuery(query, true)
        }
        keyword2.setOnClickListener{
            var query = keyword2.text.toString()
            editsearch.setQuery(query, true)
        }
        keyword3.setOnClickListener{
            var query = keyword3.text.toString()
            editsearch.setQuery(query, true)
        }
        keyword4.setOnClickListener{
            var query = keyword4.text.toString()
            editsearch.setQuery(query, true)
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun setCloseEventListener() {
        val closeButton = editsearch.findViewById(search_close_btn) as ImageView
        closeButton.setOnClickListener(View.OnClickListener {
            cleanRecyclerView()
            editsearch.setQuery("", false)
            adapter.clear()
        })
    }

    private fun setToolbar() {
        toolbar = search_toolbar
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun setRestOfTheView() {
        errorLinearLayout = web_common_error_linear
        errorLinearLayout.visibility = View.INVISIBLE
        errorImageView = web_common_error_img
        errorTextView = web_common_error_txt
        progressBar = web_common_progressbar
        progressBar.visibility = View.INVISIBLE
    }

    private fun cleanRecyclerView() {
        search_recycler_view.visibility = View.VISIBLE
        errorLinearLayout.visibility = View.INVISIBLE
    }

    private fun search(query: String) {
        val service = ApiUtils.getSearchableService()
        val query = query.replace("\\s+".toRegex(), "-")
        service.getItems(query).enqueue(object : Callback<List<RecordResponse>> {
            override fun onResponse(call: Call<List<RecordResponse>>, response: Response<List<RecordResponse>>) {
                if (!response.isSuccessful) {
                    Log.v("response", " disconnected")
                    return
                }
                val records = response.body()
                adapter.searchInRealm(query)
                modelList = adapter.add(records)
                if (modelList.isEmpty())
                    showNoData()
            }

            override fun onFailure(call: Call<List<RecordResponse>>, t: Throwable) {
                Log.v("onFailure:", "onFailure")
            }
        })
    }

    private fun setRecyclerView() {
        adapter = SearchableRecyclerAdapter(modelList)
        search_recycler_view.adapter = adapter
        search_recycler_view.visibility = View.VISIBLE
        search_recycler_view.bringToFront()
        search_recycler_view.addOnItemTouchListener(RecyclerItemClickListener(this,
                RecyclerItemClickListener.OnItemClickListener { view, position ->
                    val data = modelList.get(position)
                    showMessageBody(data)
                }))
    }

    private fun showMessageBody(data: DualModel) {
        val bundle = Bundle()
        if (data is RecordVO) {
            MsgContentGenerator.showMessageBody(baseContext, data)
        } else {
            val intent = Intent(applicationContext, WebContentActivity::class.java)
            bundle.putSerializable("record", data as RecordResponse)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    fun showNoData() {
        Toast.makeText(applicationContext, getString(R.string.no_data_in_server), Toast.LENGTH_SHORT).show()
        search_recycler_view.visibility = View.INVISIBLE
        errorLinearLayout.visibility = View.VISIBLE
        errorTextView.text = getString(R.string.no_data_in_server)
    }
}