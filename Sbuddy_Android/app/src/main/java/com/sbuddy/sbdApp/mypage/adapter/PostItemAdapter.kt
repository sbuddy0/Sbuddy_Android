package com.sbuddy.sbdApp.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sbuddy.sbdApp.databinding.MypageFeedItemBinding
import com.sbuddy.sbdApp.mypage.model.MyLike
import com.sbuddy.sbdApp.post.listener.PostItemClickListener

class PostItemAdapter(private val itemListener: PostItemClickListener) : ListAdapter<MyLike, PostItemAdapter.ViewHolder>(PostItemDiffCallback()) {

    inner class ViewHolder(private val binding: MypageFeedItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(myLike: MyLike){
            binding.item = myLike
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MypageFeedItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class PostItemDiffCallback : DiffUtil.ItemCallback<MyLike>() {
        override fun areItemsTheSame(oldItem: MyLike, newItem: MyLike): Boolean {
            return oldItem.idx_post == newItem.idx_post // 아이템의 고유 식별자를 비교하여 같은 아이템인지 확인
        }

        override fun areContentsTheSame(oldItem: MyLike, newItem: MyLike): Boolean {
            return oldItem == newItem // 아이템의 내용이 같은지 비교
        }
    }
}
