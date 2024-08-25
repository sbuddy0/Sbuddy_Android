package com.sbuddy.sbdApp.post.model

import com.sbuddy.sbdApp.http.Like
import com.sbuddy.sbdApp.http.Post
import com.sbuddy.sbdApp.http.RetrofitService
import com.sbuddy.sbdApp.http.SbuddyRetrofitService
import com.sbuddy.sbdApp.http.Search
import com.sbuddy.sbdApp.util.MetaData
import com.sbuddy.sbdApp.util.UploadUtil
import okhttp3.MultipartBody

class PostRepository {
    private val retrofitService: RetrofitService = SbuddyRetrofitService.instance()

    suspend fun postList(search: Search) = retrofitService.getList(search)
    suspend fun postLike(like: Like) = retrofitService.like(like)
    suspend fun postcancelLike(like: Like) = retrofitService.cancelLike(like)
    suspend fun postDelete(like: Like) = retrofitService.delete(like)
    suspend fun post(img: MultipartBody.Part, post: Post) =  retrofitService.post(img, UploadUtil.createJsonRequestBody(post))
    suspend fun keywordList() = retrofitService.keywordList()
}