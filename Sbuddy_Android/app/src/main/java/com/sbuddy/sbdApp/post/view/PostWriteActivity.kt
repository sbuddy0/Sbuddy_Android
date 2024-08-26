package com.sbuddy.sbdApp.post.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.ActivityPostWriteBinding
import com.sbuddy.sbdApp.post.adapter.KeywordItemAdapter
import com.sbuddy.sbdApp.post.listener.KeywordItemClickListener
import com.sbuddy.sbdApp.post.listener.PostItemClickListener
import com.sbuddy.sbdApp.post.model.Keyword
import com.sbuddy.sbdApp.post.viewmodel.PostViewModel

class PostWriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostWriteBinding
    private lateinit var postViewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_write)
        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]
        binding.viewModel = postViewModel
        binding.lifecycleOwner = this
        binding.activity = this
        setRecyclerView()
        setObserve()
    }

    fun setRecyclerView(){
        binding.oneRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.oneRecyclerview.adapter = KeywordItemAdapter { idxCategory, idxKeyword ->
            postViewModel.checkTitleKeyword(idxCategory, idxKeyword)
        }
        binding.oneRecyclerview.setHasFixedSize(true)

        binding.twoRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.twoRecyclerview.adapter = KeywordItemAdapter { idxCategory, idxKeyword ->
            postViewModel.checkSubKeyword(idxCategory, idxKeyword)
        }
        binding.twoRecyclerview.setHasFixedSize(true)
    }

    fun setObserve() {
        postViewModel.titleKeywords.observe(this) { keywords ->
            (binding.oneRecyclerview.adapter as? KeywordItemAdapter)?.submitList(keywords)
        }

        postViewModel.subKeywords.observe(this) { keywords ->
            (binding.twoRecyclerview.adapter as? KeywordItemAdapter)?.submitList(keywords)
        }
    }

    fun write(){

    }
}