package com.sbuddy.sbdApp.search.model

import com.sbuddy.sbdApp.http.RetrofitService
import com.sbuddy.sbdApp.http.SbuddyRetrofitService

class SearchRepository {
    private val retrofitService: RetrofitService = SbuddyRetrofitService.instance()

    suspend fun popularList() = retrofitService.popularList(mapOf())
    suspend fun keywordList() = retrofitService.keywordList(mapOf())
}