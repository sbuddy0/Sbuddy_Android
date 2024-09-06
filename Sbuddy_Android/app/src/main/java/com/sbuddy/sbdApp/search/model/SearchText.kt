package com.sbuddy.sbdApp.search.model

import com.sbuddy.sbdApp.post.model.File
import com.sbuddy.sbdApp.post.model.Keyword

data class SearchText(
    val file: List<File>,
    val idx_member: Int,
    val title: String,
    val keyword: List<Keyword>,
    val idx_post: Int,
    val content: String,
    val like_cnt: Int,
    val update_date: String,
    val upload_date: String
)
