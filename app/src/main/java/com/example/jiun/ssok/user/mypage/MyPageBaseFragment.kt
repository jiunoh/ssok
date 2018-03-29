package com.example.jiun.ssok.user.mypage

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.jiun.ssok.R
import com.example.jiun.ssok.SimpleViewPager
import com.example.jiun.ssok.setting.InfoActivity
import kotlinx.android.synthetic.main.fragment_my_page_base.view.*

class MyPageBaseFragment : Fragment() {
    private var currentPage = MyPageBaseViewPagerAdapter.MY_TOPIC_FRAGMENT
    private lateinit var myPageViewPager: SimpleViewPager
    private lateinit var myPageTabLayout: TabLayout
    private lateinit var informationImageButton: ImageButton
    private var currentFragment: Fragment? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_my_page_base, container, false)
        initialize(view)
        setUpElements()
        return view
    }

    private fun initialize(view: View) {
        myPageTabLayout = view.my_page_base_tab_layout
        myPageViewPager = view.my_page_base_view_pager
        myPageViewPagerAdapter = MyPageBaseViewPagerAdapter(childFragmentManager, context!!, MAX_PAGE_SIZE)
        informationImageButton = view.my_page_base_information_img_btn
    }

    private fun setUpElements() {
        myPageViewPager.adapter = myPageViewPagerAdapter
        myPageTabLayout.setupWithViewPager(myPageViewPager)
        myPageViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(myPageTabLayout))
        myPageTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                myPageViewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }
        })
        myPageViewPager.setPagingEnabled(true)
        myPageViewPager.offscreenPageLimit = MAX_PAGE_SIZE
        currentFragment = myPageViewPagerAdapter.getItem(currentPage)
        informationImageButton.setOnClickListener {
            val intent = Intent(context, InfoActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        private const val MAX_PAGE_SIZE = 3
        lateinit var myPageViewPagerAdapter: MyPageBaseViewPagerAdapter
    }
}
