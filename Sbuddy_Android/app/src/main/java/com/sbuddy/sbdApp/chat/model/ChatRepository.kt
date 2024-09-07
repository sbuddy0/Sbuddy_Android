package com.sbuddy.sbdApp.chat.model

import com.sbuddy.sbdApp.http.Message
import com.sbuddy.sbdApp.http.RetrofitService
import com.sbuddy.sbdApp.http.SbuddyRetrofitService

class ChatRepository {
    private val retrofitService: RetrofitService = SbuddyRetrofitService.instance()

    suspend fun messageList(type: String) = retrofitService.messageList(Message(type))
}