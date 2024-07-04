package com.sbuddy.sbdApp.http

import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitService {

    @POST("user/login/")
    @FormUrlEncoded
    fun login(
        @FieldMap params: Map<String, Any>
    ): Call<Map<String, Any>>


}

