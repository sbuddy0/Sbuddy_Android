package com.sbuddy.sbdApp.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sbuddy.sbdApp.databinding.MyKeywordItemBinding
import com.sbuddy.sbdApp.post.model.Keyword
import com.sbuddy.sbdApp.search.adapter.SearchRecentItemAdapter
import com.sbuddy.sbdApp.search.model.SearchRecent

class KeywordItemAdapter: ListAdapter<Keyword, KeywordItemAdapter.ViewHolder>(
    KeywordItemAdapter.KeywordItemDiffCallback()) {

    inner class ViewHolder(private val binding: MyKeywordItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(keyword: Keyword){
            binding.item = keyword
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MyKeywordItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class KeywordItemDiffCallback: DiffUtil.ItemCallback<Keyword>(){
        override fun areItemsTheSame(oldItem: Keyword, newItem: Keyword): Boolean {
            return oldItem.idx_keyword == newItem.idx_keyword
        }

        override fun areContentsTheSame(oldItem: Keyword, newItem: Keyword): Boolean {
            return oldItem == newItem
        }
    }
}