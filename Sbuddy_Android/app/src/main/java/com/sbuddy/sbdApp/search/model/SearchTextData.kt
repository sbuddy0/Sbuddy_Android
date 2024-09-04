package com.sbuddy.sbdApp.search.model

import com.sbuddy.sbdApp.post.model.File
import com.sbuddy.sbdApp.post.model.Keyword

data class SearchTextData (
    val total:Double,
    val list: List<File>,
    val idx_member: Double,
    val title: String,
    val keyword: List<Keyword>,
    val idx_post: Double,
    val content: String
)