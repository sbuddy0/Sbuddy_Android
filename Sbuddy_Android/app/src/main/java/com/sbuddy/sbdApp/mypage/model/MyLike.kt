package com.sbuddy.sbdApp.mypage.model

import com.sbuddy.sbdApp.post.model.File
import com.sbuddy.sbdApp.post.model.Keyword

data class MyLike(
    val username: String,
    val profile: String,
    val end_date: Int,
    val hits: Int,
    val file: List<File>,
    val idx_member: Int,
    val title: String,
    val keyword: List<Keyword>,
    val idx_post: Int,
    val content: String,
    val like_cnt: String,
    val update_date: String,
    val upload_date: String,
    val is_like: Boolean

){
    fun hasImage(): Boolean = file.isNotEmpty()
}
