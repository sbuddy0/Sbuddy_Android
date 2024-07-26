package com.sbuddy.sbdApp.post.model

import com.sbuddy.sbdApp.http.Like
import com.sbuddy.sbdApp.http.RetrofitService
import com.sbuddy.sbdApp.http.SbuddyRetrofitService
import com.sbuddy.sbdApp.http.Search

class PostRepository {
    private val retrofitService: RetrofitService = SbuddyRetrofitService.instance()

    suspend fun postList(search: Search) = retrofitService.getList(search)
    suspend fun postLike(like: Like) = retrofitService.like(like)
    suspend fun postcancelLike(like: Like) = retrofitService.cancelLike(like)
}