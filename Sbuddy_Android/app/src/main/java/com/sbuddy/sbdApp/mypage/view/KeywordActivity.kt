package com.sbuddy.sbdApp.mypage.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.ActivityKeywordBinding
import com.sbuddy.sbdApp.mypage.viewmodel.MypageViewModel
import com.sbuddy.sbdApp.post.adapter.KeywordItemAdapter

class KeywordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKeywordBinding
    private lateinit var mypageViewModel: MypageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_keyword)
        mypageViewModel = ViewModelProvider(this)[MypageViewModel::class.java]
        binding.viewModel = mypageViewModel
        binding.lifecycleOwner = this
        binding.activity = this
        setRecyclerView()
        setObserve()
    }

    override fun onResume() {
        super.onResume()
        mypageViewModel.loadKeywords()
    }

    fun goBefore(){
        finish()
    }

    fun setRecyclerView(){
        binding.oneRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.oneRecyclerview.adapter = KeywordItemAdapter { idxCategory, idxKeyword ->
            mypageViewModel.checkTitleKeyword(idxCategory, idxKeyword)
        }
        binding.oneRecyclerview.setHasFixedSize(true)

        binding.twoRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.twoRecyclerview.adapter = KeywordItemAdapter { idxCategory, idxKeyword ->
            mypageViewModel.checkSubKeyword(idxCategory, idxKeyword)
        }
        binding.twoRecyclerview.setHasFixedSize(true)
    }

    fun setObserve(){
        mypageViewModel.titleKeywords.observe(this) { keywords ->
            (binding.oneRecyclerview.adapter as? KeywordItemAdapter)?.submitList(keywords)
        }

        mypageViewModel.subKeywords.observe(this) { keywords ->
            (binding.twoRecyclerview.adapter as? KeywordItemAdapter)?.submitList(keywords)
        }

        mypageViewModel.keywordUpdateFinished.observe(this){
            if(it){
                finish()
            }
        }
    }
}