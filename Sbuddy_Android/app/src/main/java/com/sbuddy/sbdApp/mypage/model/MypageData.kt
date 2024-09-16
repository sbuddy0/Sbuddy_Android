package com.sbuddy.sbdApp.mypage.model

import com.sbuddy.sbdApp.post.model.Keyword

data class MypageData(
    val password: String,
    val file_name: String,
    val profile: String,
    val id: String,
    val keyword: List<Keyword>,
    val email: String,
    val username: String
)
