package com.sbuddy.sbdApp.search.model

import com.sbuddy.sbdApp.http.RetrofitService
import com.sbuddy.sbdApp.http.SbuddyRetrofitService
import com.sbuddy.sbdApp.http.SearchText

class SearchRepository {
    private val retrofitService: RetrofitService = SbuddyRetrofitService.instance()

    suspend fun popularList() = retrofitService.popularList(mapOf())
    suspend fun keywordList() = retrofitService.keywordList(mapOf())
    suspend fun searchRecentList() = retrofitService.searchRecent(mapOf())
    suspend fun searchText(text: String) = retrofitService.searchText(SearchText(text) )
}