package com.sbuddy.sbdApp.post.model


data class PostDetail (
    val hits: Double,
    val file: List<File>,
    val idx_member: Int,
    val title: String,
    val keyword:List<Keyword>,
    val content: String
)