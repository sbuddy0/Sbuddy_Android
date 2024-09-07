package com.sbuddy.sbdApp.chat.model

data class ChatResponse(
    val code: String,
    val data: ChatData,
    val message: String
)
