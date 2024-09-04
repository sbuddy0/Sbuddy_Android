package com.sbuddy.sbdApp.search.model

data class SearchItem(
    val hits: String,
    val title: String,
    val idx_post: Int,
    val like_cnt: Int,
    val upload_date: String,
    val username: String,
)
