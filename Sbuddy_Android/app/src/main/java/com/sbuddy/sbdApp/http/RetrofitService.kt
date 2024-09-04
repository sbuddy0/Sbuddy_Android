package com.sbuddy.sbdApp.http

import com.google.gson.annotations.SerializedName
import com.sbuddy.sbdApp.post.model.KeywordResponse
import com.sbuddy.sbdApp.post.model.PostDetailResponse
import com.sbuddy.sbdApp.post.model.PostResponse
import com.sbuddy.sbdApp.search.model.SearchPopularReponse
import com.sbuddy.sbdApp.search.model.SearchRecentData
import com.sbuddy.sbdApp.search.model.SearchRecentResponse
import com.sbuddy.sbdApp.search.model.SearchTextResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @POST("api/v1/login")
    @Headers("Content-Type: application/json")
    suspend fun login(
        @Body params: User
    ): Response<Any>

    @POST("api/v1/join/email/send")
    @Headers("Content-Type: application/json")
    suspend fun authEmail(
        @Body params: Email
    ): Response<Any>

    @POST("api/v1/join/email/auth")
    @Headers("Content-Type: application/json")
    suspend fun authEmailNumber(
        @Body params: EmailAuth
    ):Response<Any>

    @POST("api/v1/join")
    @Headers("Content-Type: application/json")
    suspend fun signUp(
        @Body params: SignUp
    ):Response<Any>

    @POST("api/v1/member/find/password")
    @Headers("Content-Type: application/json")
    suspend fun findPwd(
        @Body params: Email
    ): Response<Any>


    @POST("/api/v1/post/list")
    @Headers("Content-Type: application/json")
    suspend fun getList(
        @Body params: Search
    ):Response<PostResponse>

    @POST("/api/v1/post/likes")
    @Headers("Content-Type: application/json")
    suspend fun like(
        @Body params: Like
    ):Response<Any>

    @POST("/api/v1/post/likes/cancel")
    @Headers("Content-Type: application/json")
    suspend fun cancelLike(
        @Body params: Like
    ):Response<Any>

    @POST("/api/v1/post/delete")
    @Headers("Content-Type: application/json")
    suspend fun delete(
        @Body params: Like
    ):Response<Any>

    @POST("api/v1/post/likes/cancel")
    @Headers("Content-Type: application/json")
    suspend fun list(
        @FieldMap params: Map<String, String>
    ): Response<Any>

    @Multipart
    @POST("api/v1/post/write")
    suspend fun post(
//        @Header("token") authHeader: String, // 헤더를 직접 추가
        @Part img:MultipartBody.Part,
        @Part("param") params: RequestBody
    ):Response<Any>

    @POST("api/v1/keyword/list")
    @Headers("Content-Type: application/json")
    suspend fun keywordList(
        @Body params: Map<String, String>
    ): Response<KeywordResponse>

    @POST("api/v1/post/detail")
    @Headers("Content-Type: application/json")
    suspend fun detail(
        @Body params: Detail
    ): Response<PostDetailResponse>

    @POST("api/v1/post/popular/list")
    @Headers("Content-Type: application/json")
    suspend fun popularList(
        @Body params: Map<String, String>
    ): Response<SearchPopularReponse>

    @POST("api/v1/post/search/recent")
    @Headers("Content-Type: application/json")
    suspend fun searchRecent(
        @Body params: Map<String, String>
    ): Response<SearchRecentResponse>

    @POST("api/v1/post/search/text")
    @Headers("Content-Type: application/json")
    suspend fun searchText(
        @Body params: SearchText
    ): Response<Any>
}

// 로그인 유저
data class User(
    @SerializedName(value = "id") var id: String,
    @SerializedName(value = "password") var password: String
)

// 이메일
data class Email(
    @SerializedName(value = "email") var email: String
)

// 이메일 + 이메일 인증번호
data class EmailAuth(
    @SerializedName(value = "auth_code") var authCode: String,
    @SerializedName(value = "email") var email: String
)

// 회원가입
data class SignUp(
    @SerializedName(value = "username") var username: String,
    @SerializedName(value = "email") var email: String,
    @SerializedName(value = "password") var password: String
)

//
data class Search(
    @SerializedName(value = "idx_member") var idxMember: String,
    @SerializedName(value = "search") var search: String
)

data class Like(
    @SerializedName(value = "idx_post") var idxPost: Int,
    @SerializedName(value = "idx_member") var idxMember: String
)

data class Post(
    @SerializedName(value = "title") var title:String,
    @SerializedName(value = "content") var content:String,
    @SerializedName(value = "keyword_list") var keyword:ArrayList<Int>
)

data class Detail(
    @SerializedName(value = "idx_post") var idxPost: Int
)

data class SearchText(
    @SerializedName(value = "search") var text: String
)



