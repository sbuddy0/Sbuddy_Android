package com.sbuddy.sbdApp.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sbuddy.sbdApp.chat.listener.ChatItemListener
import com.sbuddy.sbdApp.chat.listener.ChatMemberListener
import com.sbuddy.sbdApp.chat.model.Chat
import com.sbuddy.sbdApp.chat.model.ChatMember
import com.sbuddy.sbdApp.databinding.ChatItemBinding
import com.sbuddy.sbdApp.databinding.ChatMemberItemBinding

class ChatMemberAdapter(private val listener: ChatMemberListener) : ListAdapter<ChatMember, ChatMemberAdapter.ViewHolder>(
    ChatMemberAdapter.ChatMemberItemDiffCallback()) {
    inner class ViewHolder(private val binding: ChatMemberItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(chatItem: ChatMember){
            binding.item = chatItem
            binding.executePendingBindings()

            binding.itemLayout.setOnClickListener{ listener.onItemClicked(chatItem.idx_member, chatItem.username) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ChatMemberItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ChatMemberItemDiffCallback : DiffUtil.ItemCallback<ChatMember>() {
        override fun areItemsTheSame(oldItem: ChatMember, newItem: ChatMember): Boolean {
            return oldItem.idx_member == newItem.idx_member// 아이템의 고유 식별자를 비교하여 같은 아이템인지 확인
        }

        override fun areContentsTheSame(oldItem: ChatMember, newItem: ChatMember): Boolean {
            return oldItem == newItem // 아이템의 내용이 같은지 비교
        }
    }
}