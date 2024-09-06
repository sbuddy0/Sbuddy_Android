package com.sbuddy.sbdApp.chat.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.chat.viewmodel.ChatViewModel
import com.sbuddy.sbdApp.databinding.FragmentChatBinding
import com.sbuddy.sbdApp.databinding.FragmentFeedBinding
import com.sbuddy.sbdApp.post.viewmodel.PostViewModel

class ChatFragment : Fragment() {
    private lateinit var binding: FragmentChatBinding
    private lateinit var chatViewModel:ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        binding.viewModel = chatViewModel
        binding.lifecycleOwner = this
        binding.fragment = this
        setRecyclerView()
        setObserve()
    }

    fun setRecyclerView(){

    }

    fun setObserve(){

    }

    fun goWriteActivity(){
        startActivity(Intent(context, ChatWriteActivity::class.java))
    }
}