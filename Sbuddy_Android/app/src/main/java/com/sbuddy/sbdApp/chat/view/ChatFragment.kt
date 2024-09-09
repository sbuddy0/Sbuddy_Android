package com.sbuddy.sbdApp.chat.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.sbuddy.sbdApp.chat.adapter.ChatReceivedItemAdapter
import com.sbuddy.sbdApp.chat.listener.ChatItemListener
import com.sbuddy.sbdApp.chat.viewmodel.ChatViewModel
import com.sbuddy.sbdApp.databinding.FragmentChatBinding

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

    override fun onResume() {
        super.onResume()
        chatViewModel.receivedChatList("R")
        chatViewModel.sendChatList("S")
    }

    fun setRecyclerView(){
        binding.recyclerViewReceive.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewReceive.adapter = ChatReceivedItemAdapter(object : ChatItemListener{
            override fun onItemClicked(idx: Int) {
                binding.contentLayout.setText(chatViewModel.receivedChats.value?.get(idx)?.content)
            }

        })
        binding.recyclerViewReceive.setHasFixedSize(true)
    }

    fun setObserve(){
        chatViewModel.receivedChats.observe(viewLifecycleOwner){ items ->
            ((binding.recyclerViewReceive.adapter) as ListAdapter<*, *>).submitList(items as List<Nothing>?)
        }
    }

    fun goWriteActivity(){
        startActivity(Intent(context, ChatWriteActivity::class.java))
    }
}