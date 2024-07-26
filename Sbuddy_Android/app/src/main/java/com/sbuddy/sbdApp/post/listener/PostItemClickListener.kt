package com.sbuddy.sbdApp.post.listener

import com.sbuddy.sbdApp.post.model.PostItem

interface PostItemClickListener {

    fun onHeartIconClicked(postItem: PostItem)
}