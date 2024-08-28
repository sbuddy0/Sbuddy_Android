package com.sbuddy.sbdApp.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sbuddy.sbdApp.databinding.SearchItemBinding
import com.sbuddy.sbdApp.post.model.PostItem
import com.sbuddy.sbdApp.post.model.PostItemAdapter
import com.sbuddy.sbdApp.search.model.SearchItem

class SearchItemAdapter(private val itemListenr: () -> Unit) : ListAdapter<SearchItem, SearchItemAdapter.ViewHolder>(
    SearchItemAdapter.SearchItemDiffCallback()
) {
    inner class ViewHolder(private val binding: SearchItemBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(searchItem: SearchItem){
            binding.item = searchItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SearchItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class SearchItemDiffCallback : DiffUtil.ItemCallback<SearchItem>() {
        override fun areItemsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem.idx_post == newItem.idx_post // 아이템의 고유 식별자를 비교하여 같은 아이템인지 확인
        }

        override fun areContentsTheSame(oldItem: SearchItem, newItem: SearchItem): Boolean {
            return oldItem == newItem // 아이템의 내용이 같은지 비교
        }
    }
}