package com.sbuddy.sbdApp.search.model

import com.sbuddy.sbdApp.post.model.File

data class SearchItem(
    val hits: String,
    val title: String,
    val file: List<File>,
    val idx_post: Int,
    val content: String,
    val like_cnt: Int,
    val upload_date: String,
    val username: String,
)
