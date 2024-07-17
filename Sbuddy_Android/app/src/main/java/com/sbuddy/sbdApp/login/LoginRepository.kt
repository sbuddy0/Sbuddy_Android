package com.sbuddy.sbdApp.login

import com.sbuddy.sbdApp.http.RetrofitService
import com.sbuddy.sbdApp.http.SbuddyRetrofitService
import com.sbuddy.sbdApp.http.User

class LoginRepository{
    private val retrofitService:RetrofitService = SbuddyRetrofitService.instance()

    suspend fun postLogin(user: User) =
        retrofitService.login(user)

}