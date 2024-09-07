package com.sbuddy.sbdApp.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sbuddy.sbdApp.chat.listener.ChatItemListener
import com.sbuddy.sbdApp.chat.model.Chat
import com.sbuddy.sbdApp.databinding.ChatItemBinding

class ChatReceivedItemAdapter(private val listener: ChatItemListener) : ListAdapter<Chat, ChatReceivedItemAdapter.ViewHolder>(
    ChatReceivedItemAdapter.ChatReceivedItemDiffCallback()) {
    inner class ViewHolder(private val binding: ChatItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(chatItem: Chat){
            binding.item = chatItem
            binding.executePendingBindings()

            binding.itemLayout.setOnClickListener{ listener.onItemClicked(chatItem.idx_message) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ChatItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ChatReceivedItemDiffCallback : DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem.idx_message == newItem.idx_message// 아이템의 고유 식별자를 비교하여 같은 아이템인지 확인
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem // 아이템의 내용이 같은지 비교
        }
    }
}