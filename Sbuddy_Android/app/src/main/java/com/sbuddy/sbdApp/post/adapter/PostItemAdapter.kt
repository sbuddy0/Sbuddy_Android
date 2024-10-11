package com.sbuddy.sbdApp.post.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sbuddy.sbdApp.databinding.FeedItemBinding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.sbuddy.sbdApp.post.listener.PostItemClickListener
import com.sbuddy.sbdApp.post.model.PostItem
import com.sbuddy.sbdApp.util.MetaData

class PostItemAdapter(private val itemListener: PostItemClickListener) : ListAdapter<PostItem, PostItemAdapter.ViewHolder>(PostItemDiffCallback()) {

    inner class ViewHolder(private val binding: FeedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            // 좋아요 버튼 클릭시
            binding.likeIcon.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    itemListener.onHeartIconClicked(item)
                }
            }
            // 점점점 버튼 클릭시
            binding.addWatch.setOnClickListener{
                if(binding.smallMenu.visibility == View.GONE){
                    binding.smallMenu.visibility = View.VISIBLE
                }else{
                    binding.smallMenu.visibility = View.GONE
                }
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val item = getItem(position)

                    Log.w("sbuddyy", "item.idx_member : " + item.idx_member)
                    Log.w("sbuddyy", "Metadata.idxMember : " + MetaData.idxMember)
                    if(item.idx_member != Integer.parseInt(MetaData.idxMember)){
                        binding.deleteButton.setTextColor(ContextCompat.getColor(binding.root.context, android.R.color.darker_gray))
                        binding.editButton.setTextColor(ContextCompat.getColor(binding.root.context, android.R.color.darker_gray))
                    }
                }

            }
            // 삭제 버튼
            binding.deleteButton.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    itemListener.onDeleteClicked(item) // item 통째는 쫌;; 나중에 수정할 것
                }
            }

            // 수정 버튼
            binding.editButton.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val item = getItem(position)
                    itemListener.onReviseClicked(item.idx_member, item.idx_post)
                }
            }


            binding.clickLayout.setOnClickListener{
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    itemListener.onItemClicked(item) // item 통째는 쫌;; 나중에 수정할 것
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