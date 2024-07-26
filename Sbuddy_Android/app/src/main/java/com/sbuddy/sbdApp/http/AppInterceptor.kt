package com.sbuddy.sbdApp.http

import com.sbuddy.sbdApp.util.MetaData
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AppInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader("token", MetaData.token)
            .build()
        proceed(newRequest)
    }
}