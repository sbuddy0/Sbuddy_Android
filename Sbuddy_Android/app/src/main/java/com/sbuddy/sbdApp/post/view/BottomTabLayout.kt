package com.sbuddy.sbdApp.post.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.tabs.TabLayout
import com.sbuddy.sbdApp.R

class BottomTabLayout: TabLayout{
    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }
    init {
        this.addTab(this.newTab().setIcon(R.drawable.outline_home_24).setText("홈"))
        this.addTab(this.newTab().setIcon(R.drawable.outline_search_24).setText("인기"))
        this.addTab(this.newTab().setIcon(R.drawable.baseline_chat_bubble_outline_24).setText("쪽지"))
        this.addTab(this.newTab().setIcon(R.drawable.outline_person_outline_24).setText("마이페이지"))

    }
}