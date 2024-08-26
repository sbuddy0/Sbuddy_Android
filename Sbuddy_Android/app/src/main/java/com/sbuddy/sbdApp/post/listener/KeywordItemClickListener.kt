package com.sbuddy.sbdApp.post.listener

import com.sbuddy.sbdApp.post.model.Keyword

fun interface KeywordItemClickListener {
    fun onItemClicked(idxCategory:String, idxKeyword:String)
}