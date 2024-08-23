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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]
        binding.viewModel = postViewModel
        binding.lifecycleOwner = this
        binding.activity = this
    }
}