package com.sbuddy.sbdApp.mypage.model

import com.sbuddy.sbdApp.http.Like
import com.sbuddy.sbdApp.http.RetrofitService
import com.sbuddy.sbdApp.http.SbuddyRetrofitService

class MypageRepository {
    private val retrofitService: RetrofitService = SbuddyRetrofitService.instance()

    suspend fun myDetail() = retrofitService.myDetail(mapOf())
    suspend fun myLikeList() = retrofitService.myLikeList(mapOf())
    suspend fun myWriteList() = retrofitService.myWriteList(mapOf())
    suspend fun postLike(like: Like) = retrofitService.like(like)
    suspend fun postcancelLike(like: Like) = retrofitService.cancelLike(like)
    suspend fun postDelete(like: Like) = retrofitService.delete(like)
}