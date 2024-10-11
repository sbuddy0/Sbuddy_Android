package com.sbuddy.sbdApp.signup.model

import com.sbuddy.sbdApp.http.Email
import com.sbuddy.sbdApp.http.EmailAuth
import com.sbuddy.sbdApp.http.KeywordList
import com.sbuddy.sbdApp.http.RetrofitService
import com.sbuddy.sbdApp.http.SbuddyRetrofitService
import com.sbuddy.sbdApp.http.SignUp

class SignUpRepository {
    private val retrofitService: RetrofitService = SbuddyRetrofitService.instance()

    suspend fun postAuthEmail(email: Email) = retrofitService.authEmail(email)
    suspend fun postAuthNumber(emailAuth: EmailAuth) = retrofitService.authEmailNumber(emailAuth)
    suspend fun postSignUp(signUp: SignUp) = retrofitService.signUp(signUp)

    suspend fun keywordList() = retrofitService.keywordList(mapOf())
    suspend fun insertKeyword(str: String) = retrofitService.insertKeyword(KeywordList(str))
}