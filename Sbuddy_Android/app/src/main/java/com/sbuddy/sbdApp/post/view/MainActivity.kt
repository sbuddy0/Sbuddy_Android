package com.sbuddy.sbdApp.post.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.ActivityMainBinding
import com.sbuddy.sbdApp.post.viewmodel.PostViewModel

class MainActivity : FragmentActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var postViewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    binding =  DataBindingUtil.setContentView(this, R.layout.activity_main)
    postViewModel = ViewModelProvider(this)[PostViewModel::class.java]
    binding.viewModel = postViewModel
    binding.lifecycleOwner = this
    binding.activity = this

    setViewPager()
    setTabLayout()

    }

    fun setTabLayout(){
        binding.mainTab.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.mainPager.setCurrentItem(tab!!.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

    }

    fun setViewPager(){
        binding.mainPager.adapter = MainPagerAdapter(this, 4)
        binding.mainPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                binding.mainTab.getTabAt(position)?.select()
            }
        })
    }

}

