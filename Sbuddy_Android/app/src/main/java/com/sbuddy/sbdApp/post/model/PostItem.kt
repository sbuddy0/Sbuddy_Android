package com.sbuddy.sbdApp.post.model

import android.util.Log
import android.view.View

data class PostItem (
    val idx_post: Int,
    val idx_member: Int,
    val username: String,
    val title: String,
    val content: String,
    val file_path: String,
    val profile: String,
    val is_like: Boolean,
    val like_cnt: String,
    val upload_date: String,
    val update_date: String?,
    val keyword_list: List<Keyword>
        ) {

    fun hasImage(): Boolean = file_path.isNotEmpty()
}