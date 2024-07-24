package com.sbuddy.sbdApp.post.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.FragmentFeedBinding
import com.sbuddy.sbdApp.post.model.PostItem
import com.sbuddy.sbdApp.post.model.PostItemAdapter
import com.sbuddy.sbdApp.post.viewmodel.PostViewModel

class FeedFragment : Fragment() {
    private lateinit var binding: FragmentFeedBinding
    private lateinit var postViewModel: PostViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]
        binding.viewModel = postViewModel
        binding.lifecycleOwner = this
        setRecyclerView()
        setObserve()
    }

    override fun onResume() {
        super.onResume()
        postViewModel.loadItems()
    }

    fun setRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = PostItemAdapter()
    }


    fun setObserve() {
        postViewModel.items.observe(viewLifecycleOwner, Observer { items ->
            Log.w("sbuddyy", "items 바뀜")
            ((binding.recyclerView.adapter) as ListAdapter<*, *>).submitList(items as List<Nothing>?)
        })

        postViewModel.showToast.observe(viewLifecycleOwner, Observer { showToast ->
            if (showToast) {
                // TODO: Toast 메시지 보여주는 로직 추가
            }
        })
    }
}