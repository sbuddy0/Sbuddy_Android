package com.sbuddy.sbdApp.post.model

import java.io.File

data class PostDetail (
    val file: List<File>,
    val idx_member: Int,
    val title: String,
    val keyword:List<Keyword>,
    val content: String
)