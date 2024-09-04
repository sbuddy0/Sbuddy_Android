package com.sbuddy.sbdApp.search.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.ActivitySearchViewBinding
import com.sbuddy.sbdApp.search.adapter.SearchRecentItemAdapter
import com.sbuddy.sbdApp.search.listener.SearchRecentClickListener
import com.sbuddy.sbdApp.search.viewmodel.SearchViewModel
import com.sbuddy.sbdApp.util.KeywordView

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

        setRecyclerView()
        setObserve()
        addKeywordView()
        searchViewModel.searchRecentList()
    }

    fun setRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = SearchRecentItemAdapter(object:SearchRecentClickListener{
            override fun onTextClicked(idx: Double) {
                TODO("Not yet implemented")
            }

            override fun onDeleteClicked() {
                TODO("Not yet implemented")
            }
        })
        binding.recyclerView.setHasFixedSize(true)
    }
    fun setObserve(){
        searchViewModel.searchRecentList.observe(this){
            ((binding.recyclerView.adapter) as ListAdapter<*, *>).submitList(searchViewModel.searchRecentList.value as List<Nothing>?)
            Log.d("searchh", "searchViewModel.searchRecentList.value : " + searchViewModel.searchRecentList.value )
        }
    }



    fun addKeywordView(){
        searchViewModel.loadKeywords{
            val keywords = searchViewModel.keywords
            for(i in 0 until keywords.value!!.size){
                var keywordView = KeywordView(this)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(20, 20, 20, 20)
                keywordView.layoutParams = layoutParams
                keywordView.setText(keywords.value!!.get(i).keyword)
                binding.keywordLayout.addView(keywordView)
            }
        }
    }

    fun searchText(){
        searchViewModel.searchText(binding.search.text.toString())
    }

    fun goBefore(){
        finish()
    }
}