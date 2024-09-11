package com.sbuddy.sbdApp.chat.view

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.chat.adapter.ChatReceivedItemAdapter
import com.sbuddy.sbdApp.chat.adapter.ChatSendItemAdapter
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

        setReceivedRecyclerView()
        setSendRecyclerView()
        setObserve()
    }

    override fun onResume() {
        super.onResume()

        chatViewModel.receivedChatList("R")
        chatViewModel.sendChatList("S")
    }

    fun setReceivedRecyclerView(){
        binding.recyclerViewReceive.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewReceive.adapter = ChatReceivedItemAdapter(object : ChatItemListener{
            override fun onItemClicked(idx: Int) {
                // item에서 그 번호인걸 찾기
                for(i in 0 until chatViewModel.receivedChats.value!!.size){
                    if(idx == chatViewModel.receivedChats.value!!.get(i).idx_message){
                        binding.contentLayout.setText(chatViewModel.receivedChats.value?.get(i)?.content)
                        break
                    }
                }
            }

        })
        binding.recyclerViewReceive.setHasFixedSize(true)
    }

    fun setSendRecyclerView(){
        binding.recyclerViewSend.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewSend.adapter = ChatSendItemAdapter(object : ChatItemListener{
            override fun onItemClicked(idx: Int) {
                // item에서 그 번호인걸 찾기
                for(i in 0 until chatViewModel.sendChats.value!!.size){
                    if(idx == chatViewModel.sendChats.value!!.get(i).idx_message){
                        binding.contentLayout.setText(chatViewModel.sendChats.value?.get(i)?.content)
                        break
                    }
                }
            }

        })
        binding.recyclerViewSend.setHasFixedSize(true)
    }

    fun setObserve(){
        chatViewModel.receivedChats.observe(viewLifecycleOwner){ items ->
            ((binding.recyclerViewReceive.adapter) as ListAdapter<*, *>).submitList(items as List<Nothing>?)
            Log.e("chatt", "receivedChats 바뀌ㅣㅁ : " + items)

        }

        chatViewModel.sendChats.observe(viewLifecycleOwner){items ->
            ((binding.recyclerViewSend.adapter) as ListAdapter<*, *>).submitList(items as List<Nothing>?)
        }

        chatViewModel.buttonIsReceived.observe(viewLifecycleOwner){
            if(it){
                binding.receivedBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_rounded_corner_rectangle)
                binding.receivedBtn.setTextColor(android.graphics.Color.WHITE)
                binding.recyclerViewReceive.visibility = View.VISIBLE
                binding.contentLayout.setText(chatViewModel.receivedChats.value?.get(0)?.content)
            }else{
                binding.receivedBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_rounded_corner_rectangle_gray)
                binding.receivedBtn.setTextColor(android.graphics.Color.DKGRAY)
                binding.recyclerViewReceive.visibility = View.GONE
            }
        }

        chatViewModel.buttonIsSend.observe(viewLifecycleOwner){
            if(it){
                binding.sendBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_rounded_corner_rectangle)
                binding.sendBtn.setTextColor(android.graphics.Color.WHITE)
                binding.recyclerViewSend.visibility = View.VISIBLE
                binding.contentLayout.setText(chatViewModel.receivedChats.value?.get(0)?.content)
            }else{
                // 버튼 모양
                binding.sendBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_rounded_corner_rectangle_gray)
                binding.sendBtn.setTextColor(android.graphics.Color.DKGRAY)
                binding.recyclerViewSend.visibility = View.GONE
            }
        }
    }

    fun goWriteActivity(){
        startActivity(Intent(context, ChatWriteActivity::class.java))
    }
}