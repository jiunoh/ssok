package com.example.jiun.sookpam

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.jiun.sookpam.searchable.SearchableActivity
import kotlinx.android.synthetic.main.fragment_message_base.view.*

class MessageBaseFragment : Fragment() {
    private var currentPage = MessageBaseViewPagerAdapter.COMMON_FRAGMENT
    private lateinit var messageViewPager: SimpleViewPager
    private lateinit var messageTabLayout: TabLayout
    private lateinit var searchImageButton: ImageButton
    private var currentFragment: Fragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_message_base, container, false)
        initialize(view)
        setUpElements()
        return view
    }

    private fun initialize(view: View) {
        searchImageButton = view.message_base_search_img_btn
        messageTabLayout = view.message_base_tab_layout
        messageViewPager = view.message_base_view_pager
        messageViewPagerAdapter = MessageBaseViewPagerAdapter(childFragmentManager, context!!, MAX_PAGE_SIZE)
    }

    private fun setUpElements() {
        searchImageButton.setOnClickListener {
            val intent = Intent(context, SearchableActivity::class.java)
            startActivity(intent)
        }
        messageViewPager.adapter = messageViewPagerAdapter
        messageTabLayout.setupWithViewPager(messageViewPager)
        messageViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(messageTabLayout))
        messageTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                messageViewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
            }
        })
        messageViewPager.setPagingEnabled(true)
        currentFragment = messageViewPagerAdapter.getItem(currentPage)
    }

    companion object {
        private const val MAX_PAGE_SIZE = 2
        lateinit var messageViewPagerAdapter: MessageBaseViewPagerAdapter
    }
}
