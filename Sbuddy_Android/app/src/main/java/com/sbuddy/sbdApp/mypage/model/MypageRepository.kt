package com.sbuddy.sbdApp.mypage.model

import com.sbuddy.sbdApp.http.Detail
import com.sbuddy.sbdApp.http.Keyword
import com.sbuddy.sbdApp.http.Like
import com.sbuddy.sbdApp.http.RetrofitService
import com.sbuddy.sbdApp.http.SbuddyRetrofitService
import com.sbuddy.sbdApp.http.Update
import com.sbuddy.sbdApp.util.UploadUtil
import okhttp3.MultipartBody

class MypageRepository {
    private val retrofitService: RetrofitService = SbuddyRetrofitService.instance()

    suspend fun myDetail() = retrofitService.myDetail(mapOf())
    suspend fun myLikeList() = retrofitService.myLikeList(mapOf())
    suspend fun myWriteList() = retrofitService.myWriteList(mapOf())
    suspend fun postLike(like: Like) = retrofitService.like(like)
    suspend fun postcancelLike(like: Like) = retrofitService.cancelLike(like)
    suspend fun postDelete(like: Like) = retrofitService.delete(like)
    suspend fun keywordList() = retrofitService.keywordList(mapOf())
    suspend fun upadateKeyword(keyword: String) = retrofitService.updateKeyword(Keyword(keyword))
    suspend fun revise(img: MultipartBody.Part?, update: Update) = retrofitService.revise(img, UploadUtil.createJsonRequestBody(update))
    suspend fun detail(detail: Detail) = retrofitService.detail(detail)
}