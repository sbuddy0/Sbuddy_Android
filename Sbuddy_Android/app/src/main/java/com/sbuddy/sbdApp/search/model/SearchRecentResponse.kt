package com.sbuddy.sbdApp.search.model

import com.sbuddy.sbdApp.http.SearchText

data class SearchRecentResponse (
    val code: String,
    val data: SearchRecentData,
    val message: String
)