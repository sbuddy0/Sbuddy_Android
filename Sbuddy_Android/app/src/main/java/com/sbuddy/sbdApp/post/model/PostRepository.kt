package com.sbuddy.sbdApp.post.model

import com.sbuddy.sbdApp.http.RetrofitService
import com.sbuddy.sbdApp.http.SbuddyRetrofitService
import com.sbuddy.sbdApp.http.Search

class PostRepository {
    private val retrofitService: RetrofitService = SbuddyRetrofitService.instance()

    suspend fun postList(search: Search) = retrofitService.getList(search)
}