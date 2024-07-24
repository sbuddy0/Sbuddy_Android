package com.sbuddy.sbdApp.post.model

data class PostItem (
    val idx_post: Int,
    val username: String,
    val title: String,
    val content: String,
    val file_path: String,
    val profile: String,
    val like_cnt: String,
    val upload_date: String,
    val update_date: String?
        )