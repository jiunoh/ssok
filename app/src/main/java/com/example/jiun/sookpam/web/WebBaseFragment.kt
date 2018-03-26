package com.example.jiun.sookpam.web

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import kotlinx.android.synthetic.main.fragment_web_base.view.*
import com.example.jiun.sookpam.searchable.SearchableActivity
import android.content.Intent
import com.example.jiun.sookpam.R
import com.example.jiun.sookpam.SimpleViewPager

class WebBaseFragment : Fragment() {
    private var currentPage = WebBaseViewPagerAdapter.RECOMMEND_FRAGMENT
    private lateinit var webViewPager: SimpleViewPager
    private lateinit var webViewPagerAdapter: WebBaseViewPagerAdapter
    private lateinit var webTabLayout: TabLayout
    private lateinit var searchImageButton: ImageButton
    private var currentFragment: Fragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_web_base, container, false)
        initialize(view)
        setUpElements()
        return view
    }

    private fun initialize(view: View) {
        searchImageButton = view.web_base_search_img_btn
        webTabLayout = view.web_base_tab_layout
        webViewPager = view.web_base_view_pager
        webViewPagerAdapter = WebBaseViewPagerAdapter(childFragmentManager, context!!, MAX_PAGE_SIZE)
    }

    private fun setUpElements() {
        searchImageButton.setOnClickListener {
            val intent = Intent(context, SearchableActivity::class.java)
            startActivity(intent) }
        webViewPager.adapter = webViewPagerAdapter
        webTabLayout.setupWithViewPager(webViewPager)
        webViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(webTabLayout))
        webTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                webViewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }
        })
        webViewPager.setPagingEnabled(true)
        webViewPager.offscreenPageLimit = MAX_PAGE_SIZE
        currentFragment = webViewPagerAdapter.getItem(currentPage)
    }

    companion object {
        private const val MAX_PAGE_SIZE = 3
    }
}
