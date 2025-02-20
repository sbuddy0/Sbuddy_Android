package com.sbuddy.sbdApp.http

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SbuddyRetrofitService {
    fun instance(): RetrofitService {
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        val appInterceptor = AppInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        clientBuilder.apply {
            addInterceptor(loggingInterceptor)
            addInterceptor(appInterceptor)
        }


        val retrofit = Retrofit.Builder()
            .baseUrl("http://54.252.34.20:8082/")
//            .baseUrl("http://10.0.2.2:8082/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client((clientBuilder.build()))
            .build()
        val retrofitService = retrofit.create(RetrofitService::class.java)
        return retrofitService
    }

}