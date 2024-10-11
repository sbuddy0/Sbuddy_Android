package com.sbuddy.sbdApp.post.listener

import com.sbuddy.sbdApp.post.model.PostItem

interface PostItemClickListener {

    fun onHeartIconClicked(postItem: PostItem)
    fun onDeleteClicked(postItem: PostItem)
    fun onReviseClicked(idxMember: Int, position: Int)
    fun onItemClicked(postItem: PostItem)
}