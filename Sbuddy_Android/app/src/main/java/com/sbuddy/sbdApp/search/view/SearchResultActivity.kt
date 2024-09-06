package com.sbuddy.sbdApp.search.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.ActivitySearchResultBinding
import com.sbuddy.sbdApp.search.adapter.SearchItemAdapter
import com.sbuddy.sbdApp.search.viewmodel.SearchViewModel

class SearchResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchResultBinding
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_result)
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding.viewModel = searchViewModel
        binding.lifecycleOwner = this
        binding.activity = this

        setRecyclerView()
        setObserve()
        search()
    }

    override fun onResume() {
        super.onResume()

    }

    fun search(){
        val text = intent.getStringExtra("txt")
        Log.d("textt", "intent text : " + text)
        binding.text.setText("\"" + text + "\"")
        searchViewModel.searchText(text!!, object:SearchViewModel.SearchViewModelListener{
            override fun onItemIsNull() {
                Log.d("resultt", "onItemIsNull")
                binding.resultNull.visibility = View.VISIBLE
            }
        })
    }

    fun setObserve(){
        searchViewModel.items.observe(this){
            ((binding.recyclerView.adapter) as ListAdapter<*, *>).submitList(searchViewModel.items.value as List<Nothing>?)
            Log.d("searchh", "searchViewModel.searchRecentList.value : " + searchViewModel.items.value )
        }
    }

    fun setRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = SearchItemAdapter {

        }
        binding.recyclerView.setHasFixedSize(true)
    }

    fun goBefore(){
        finish()
    }
}