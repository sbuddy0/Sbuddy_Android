package com.sbuddy.sbdApp.chat.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.chat.viewmodel.ChatViewModel
import com.sbuddy.sbdApp.databinding.ActivityChatWriteBinding
import com.sbuddy.sbdApp.databinding.ActivityPostDetailBinding
import com.sbuddy.sbdApp.post.viewmodel.PostViewModel

class ChatWriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatWriteBinding
    private lateinit var chatViewModel: ChatViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat_write)
        chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        binding.viewModel = chatViewModel
        binding.lifecycleOwner = this
        binding.activity = this

    }

    fun goBefore(){
        finish()
    }
}