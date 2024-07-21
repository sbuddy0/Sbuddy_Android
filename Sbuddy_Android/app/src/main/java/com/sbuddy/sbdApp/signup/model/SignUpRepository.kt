package com.sbuddy.sbdApp.signup.model

import com.sbuddy.sbdApp.http.Email
import com.sbuddy.sbdApp.http.RetrofitService
import com.sbuddy.sbdApp.http.SbuddyRetrofitService

class SignUpRepository {
    private val retrofitService: RetrofitService = SbuddyRetrofitService.instance()

    suspend fun postAuthEmail(email: String) = retrofitService.authEmail(Email(email))
}