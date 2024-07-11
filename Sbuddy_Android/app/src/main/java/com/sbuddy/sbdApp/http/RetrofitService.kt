package com.sbuddy.sbdApp.http

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetrofitService {

    @POST("api/v1/login")
    @Headers("Content-Type: application/json")
    fun login(
        @Body params: User
    ): Call<Object>


    @POST("api/v1/post/popular/list")
    @Headers("Content-Type: application/json")
//    @FormUrlEncoded
    fun list(
        @FieldMap params: Map<String, String>
    ): Call<Object>

}

data class User(
    @SerializedName(value = "id") var id: String,
    @SerializedName(value = "password") var password: String
)

