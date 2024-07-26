package com.sbuddy.sbdApp.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sbuddy.sbdApp.databinding.FeedItemBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.sbuddy.sbdApp.post.listener.PostItemClickListener
import com.sbuddy.sbdApp.post.model.PostItem

class PostItemAdapter(private val itemListener: PostItemClickListener) : ListAdapter<PostItem, PostItemAdapter.ViewHolder>(PostItemDiffCallback()) {

    inner class ViewHolder(private val binding: FeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // 클릭 리스너 설정
            binding.likeIcon.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    itemListener.onHeartIconClicked(item)
                }
            }
        }
        fun bind(postItem: PostItem) {
            binding.item = postItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FeedItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) // ListAdapter에서 제공하는 메서드로 아이템 가져오기
        holder.bind(item)
    }

    class PostItemDiffCallback : DiffUtil.ItemCallback<PostItem>() {
        override fun areItemsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
            return oldItem.idx_post == newItem.idx_post // 아이템의 고유 식별자를 비교하여 같은 아이템인지 확인
        }

        override fun areContentsTheSame(oldItem: PostItem, newItem: PostItem): Boolean {
            return oldItem == newItem // 아이템의 내용이 같은지 비교
        }
    }
}