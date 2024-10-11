package com.sbuddy.sbdApp.post.model

data class PostDetailData (
    val total: Double,
    val file: List<File>,
    val idx_member: Double,
    val title: String,
    val profile: String,
    val keyword: List<Keyword>,
    val idx_post: Double,
    val content: String
)