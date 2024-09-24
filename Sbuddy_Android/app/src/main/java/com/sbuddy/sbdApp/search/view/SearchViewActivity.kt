package com.sbuddy.sbdApp.search.view

import android.app.appsearch.SearchResult
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.sbuddy.sbdApp.util.ToastMessage

class SearchViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchViewBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_view)
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding.viewModel = searchViewModel
        binding.lifecycleOwner = this
        binding.activity = this
        context = this

        setRecyclerView()
        setObserve()
        addKeywordView()
        searchViewModel.searchRecentList()
    }

    override fun onResume() {
        super.onResume()
        searchViewModel.searchRecentList()
    }

    fun setRecyclerView() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = SearchRecentItemAdapter(object : SearchRecentClickListener {
            override fun onTextClicked(text: String) {
                val intent = Intent(context, SearchResultActivity::class.java)
                intent.putExtra("txt", text)
                startActivity(intent)
            }

            override fun onDeleteClicked(idx: Double) {
                searchViewModel.deleteRecent(idx.toInt())
            }
        })
        binding.recyclerView.setHasFixedSize(true)
    }

    fun setObserve() {
        searchViewModel.searchRecentList.observe(this) {
            ((binding.recyclerView.adapter) as ListAdapter<*, *>).submitList(searchViewModel.searchRecentList.value as List<Nothing>?)
            Log.d(
                "searchh",
                "searchViewModel.searchRecentList.value : " + searchViewModel.searchRecentList.value
            )
        }

        searchViewModel.recentIsNull.observe(this){
            if(it){
                binding.recentResultNull.visibility = View.VISIBLE
            }else{
                binding.recentResultNull.visibility = View.GONE
            }
        }

        searchViewModel.showToast.observe(this){
            if(it){
                ToastMessage.show(this, "요청 실패")
            }
        }
//        searchViewModel.searchTextIsNull.observe(this){
//            binding.recentResultNull.visibility = View.VISIBLE
//        }
    }


    fun addKeywordView() {
        searchViewModel.loadKeywords {
            val keywords = searchViewModel.keywords
            for (i in 0 until keywords.value!!.size) {
                var keywordView = KeywordView(this)
                val text = keywords.value!!.get(i).keyword

                keywordView.setOnClickListener {
                    val intent = Intent(this, SearchResultActivity::class.java)
                    intent.putExtra("txt", text)
                    startActivity(intent)
                }
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(20, 20, 20, 20)
                keywordView.layoutParams = layoutParams
                keywordView.setText(text)
                binding.keywordLayout.addView(keywordView)
            }
        }
    }

    fun searchText() {
        val intent = Intent(this, SearchResultActivity::class.java)
        intent.putExtra("txt", binding.search.text.toString())
        Log.d("textt", "txt: " + binding.search.text)
        this.startActivity(intent)
    }

    fun goBefore() {
        finish()
    }
}