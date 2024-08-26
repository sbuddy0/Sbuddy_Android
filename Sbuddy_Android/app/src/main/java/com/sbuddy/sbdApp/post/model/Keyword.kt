package com.sbuddy.sbdApp.post.model

data class Keyword (
    val idx_keyword: Int,
    val code: String,
    val idx_category: String?,
    val keyword: String,
    var isChecked: Boolean
)