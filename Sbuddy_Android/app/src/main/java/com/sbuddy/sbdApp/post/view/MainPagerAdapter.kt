package com.sbuddy.sbdApp.post.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sbuddy.sbdApp.chat.view.ChatFragment
import com.sbuddy.sbdApp.mypage.view.MypageFragment
import com.sbuddy.sbdApp.search.view.SearchFragment

class MainPagerAdapter(
    fragmentActivity : FragmentActivity,
    val tabCount : Int
):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return tabCount
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return FeedFragment()
            1 -> return SearchFragment()
            2 -> return ChatFragment()
            else -> return MypageFragment()
        }
    }
}