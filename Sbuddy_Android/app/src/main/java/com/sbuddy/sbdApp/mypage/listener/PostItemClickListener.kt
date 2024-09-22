package com.sbuddy.sbdApp.mypage.listener

import com.sbuddy.sbdApp.mypage.model.MyLike
import com.sbuddy.sbdApp.post.model.PostItem

interface PostItemClickListener {
    fun onHeartIconClicked(myLike: MyLike)
    fun onDeleteClicked(myLike: MyLike)
    fun onReviseClicked(myLike: MyLike)
    fun onItemClicked(myLike: MyLike)
}