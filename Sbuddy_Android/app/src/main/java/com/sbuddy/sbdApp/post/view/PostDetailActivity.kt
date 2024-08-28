package com.sbuddy.sbdApp.post.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.ActivityPostDetailBinding
import com.sbuddy.sbdApp.post.viewmodel.PostViewModel

class PostDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostDetailBinding
    private lateinit var postViewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail)
        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]
        binding.viewModel = postViewModel
        binding.lifecycleOwner = this
        binding.activity = this

        setObserve()
        detail()
    }

    fun setObserve(){
        postViewModel.detail
    }

    fun detail(){
        // postviewModel의 아이템에서 현재 idx 가져옴\
        val intent = getIntent()
        val idxPost = intent.getIntExtra("idx_post", 0)
        postViewModel.loadItems{
            postViewModel.setItem(idxPost)
            postViewModel.detail(idxPost)
        }
    }

    fun goBefore(){
        finish()
    }
}