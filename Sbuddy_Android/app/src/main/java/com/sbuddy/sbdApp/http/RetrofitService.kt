package com.sbuddy.sbdApp.http

import com.google.gson.annotations.SerializedName
import com.sbuddy.sbdApp.post.model.PostResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
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




