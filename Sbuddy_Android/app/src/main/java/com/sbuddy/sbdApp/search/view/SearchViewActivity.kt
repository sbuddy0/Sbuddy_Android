package com.sbuddy.sbdApp.search.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.ActivitySearchViewBinding
import com.sbuddy.sbdApp.post.listener.LoadListener
import com.sbuddy.sbdApp.post.viewmodel.PostViewModel
import com.sbuddy.sbdApp.search.viewmodel.SearchViewModel

class SearchViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchViewBinding
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_view)
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding.viewModel = searchViewModel
        binding.lifecycleOwner = this
        binding.activity = this

        setObserve()
        addKeywordView()
    }

    fun setObserve(){

    }

    fun addKeywordView(){
        searchViewModel.loadKeywords{
            val keywords = searchViewModel.keywords
            for(i in 0 until keywords.value!!.size){
                
            }
        }
    }
}