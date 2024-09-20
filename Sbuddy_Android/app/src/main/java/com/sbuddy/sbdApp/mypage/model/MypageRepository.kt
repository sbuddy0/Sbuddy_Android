package com.sbuddy.sbdApp.mypage.model

import com.sbuddy.sbdApp.http.RetrofitService
import com.sbuddy.sbdApp.http.SbuddyRetrofitService

class MypageRepository {
    private val retrofitService: RetrofitService = SbuddyRetrofitService.instance()

    suspend fun myDetail() = retrofitService.myDetail(mapOf())
    suspend fun myLikeList() = retrofitService.myLikeList(mapOf())
    suspend fun myWriteList() = retrofitService.myWriteList(mapOf())
}