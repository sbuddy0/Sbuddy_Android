package com.sbuddy.sbdApp.http

import com.google.gson.annotations.SerializedName
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


    @POST("api/v1/post/popular/list")
    @Headers("Content-Type: application/json")
//    @FormUrlEncoded
    suspend fun list(
        @FieldMap params: Map<String, String>
    ): Response<Any>

}

data class User(
    @SerializedName(value = "id") var id: String,
    @SerializedName(value = "password") var password: String
)

data class Email(
    @SerializedName(value = "email") var email: String
)

