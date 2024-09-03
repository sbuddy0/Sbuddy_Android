package com.sbuddy.sbdApp.search.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sbuddy.sbdApp.databinding.SearchRecentItemBinding
import com.sbuddy.sbdApp.search.listener.SearchRecentClickListener
import com.sbuddy.sbdApp.search.model.SearchRecent

class SearchRecentItemAdapter(private val listener: SearchRecentClickListener): ListAdapter<SearchRecent, SearchRecentItemAdapter.ViewHolder>(SearchRecentItemAdapter.SearchItemDiffCallback()) {
    inner class ViewHolder(private val binding: SearchRecentItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(searchRecent: SearchRecent){
            binding.item = searchRecent
            binding.executePendingBindings()

            val item = binding.item as SearchRecent
            binding.text.setOnClickListener{listener.onTextClicked(item.idx_search_text)}
            binding.delete.setOnClickListener{listener.onDeleteClicked()}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.sbuddy.sbdApp.search.adapter.SearchRecentItemAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SearchRecentItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        Log.d("searchh", "onBindViewHolder : " + position)
    }


    class SearchItemDiffCallback : DiffUtil.ItemCallback<SearchRecent>() {
        override fun areItemsTheSame(oldItem: SearchRecent, newItem: SearchRecent): Boolean {
            return oldItem.idx_search_text == newItem.idx_search_text // 아이템의 고유 식별자를 비교하여 같은 아이템인지 확인
        }

        override fun areContentsTheSame(oldItem: SearchRecent, newItem: SearchRecent): Boolean {
            return oldItem == newItem // 아이템의 내용이 같은지 비교
        }
    }
}