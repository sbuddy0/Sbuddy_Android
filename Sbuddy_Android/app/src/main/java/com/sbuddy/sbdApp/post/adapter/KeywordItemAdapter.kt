package com.sbuddy.sbdApp.post.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sbuddy.sbdApp.databinding.KeywordItemBinding
import com.sbuddy.sbdApp.post.listener.KeywordItemClickListener
import com.sbuddy.sbdApp.post.listener.PostItemClickListener
import com.sbuddy.sbdApp.post.model.Keyword

class KeywordItemAdapter(private val itemListener: KeywordItemClickListener) : ListAdapter<Keyword, KeywordItemAdapter.ViewHolder>(KeywordDiffCallback()) {
    inner class ViewHolder(private val binding: KeywordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                binding.keywordText.setOnClickListener{
                    itemListener.onItemClicked(binding.item!!.idx_category.toString(), binding.item!!.idx_keyword.toString())
                }

            }
        fun bind(keyword: Keyword) {
            binding.item = keyword
            binding.keywordText.setTextColor(
                if (keyword.isChecked) android.graphics.Color.parseColor("#998DFF")
                else android.graphics.Color.BLACK // 기본 색상
            )
            binding.executePendingBindings()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = KeywordItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class KeywordDiffCallback : DiffUtil.ItemCallback<Keyword>() {
        override fun areItemsTheSame(oldItem: Keyword, newItem: Keyword): Boolean {
            return oldItem.idx_keyword == newItem.idx_keyword
        }

        override fun areContentsTheSame(oldItem: Keyword, newItem: Keyword): Boolean {
            return oldItem == newItem
        }

    }
}