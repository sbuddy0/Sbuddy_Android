package com.sbuddy.sbdApp.chat.view

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.chat.adapter.ChatMemberAdapter
import com.sbuddy.sbdApp.chat.listener.ChatItemListener
import com.sbuddy.sbdApp.chat.listener.ChatMemberListener
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

        setRecyclerView()
        setObserve()
    }

    fun setRecyclerView(){
        binding.recyclerView.layoutManager =  LinearLayoutManager(this)
        binding.recyclerView.adapter = ChatMemberAdapter(object: ChatMemberListener {
            override fun onItemClicked(idx: Int, nickname: String) {
                chatViewModel.setCurrentMember(idx)
                binding.userText.setText(nickname)
                binding.recyclerView.visibility = View.GONE
            }
        })
    }

    fun setObserve(){
        chatViewModel.memberList.observe(this){
            ((binding.recyclerView.adapter) as ListAdapter<*, *>).submitList(it as List<Nothing>?)
            binding.recyclerView.visibility = View.VISIBLE
        }

        chatViewModel.isMsgSend.observe(this){
            if(it){
                finish()
            }
        }
    }

    fun goBefore(){
        finish()
    }

    fun requestMember(){
        chatViewModel.member(binding.userText.text.toString())
    }

    fun requestSendMsg(){
        chatViewModel.sendMessage(binding.contentEdit.text.toString())
    }
}
