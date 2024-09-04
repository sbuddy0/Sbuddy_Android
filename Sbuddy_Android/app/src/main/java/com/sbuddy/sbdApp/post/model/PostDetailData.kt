package com.sbuddy.sbdApp.post.model

data class PostDetailData (
    val total: Double,
    val list: List<SearchFile>,
    val idx_member: Double,
    val title: String,
    val keyword: List<Keyword>,
    val idx_post: Double,
    val content: String
)