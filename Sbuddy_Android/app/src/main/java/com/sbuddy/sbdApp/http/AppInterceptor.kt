package com.sbuddy.sbdApp.http

import com.sbuddy.sbdApp.util.MetaData
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AppInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain) : Response {

        val newRequest = chain.request().newBuilder()
            .addHeader("token", MetaData.token)
            .build()

        return chain.proceed(newRequest)
    }
}