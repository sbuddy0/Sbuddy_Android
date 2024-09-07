package com.sbuddy.sbdApp.chat.model

data class Chat (
    val idx_sender: Int,
    val sender_username: String,
    val sender_email:String,
    val idx_receiver: Int,
    val send_date: String,
    val idx_message: Int,
    val title: String,
    val idx_reply: String,
    val content: String
)