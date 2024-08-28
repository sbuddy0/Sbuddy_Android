package com.sbuddy.sbdApp.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.sbuddy.sbdApp.databinding.FragmentSearchBinding
import com.sbuddy.sbdApp.search.adapter.SearchItemAdapter
import com.sbuddy.sbdApp.search.viewmodel.SearchViewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        binding.viewModel = searchViewModel
        binding.lifecycleOwner = this
        binding.fragment = this
        setRecyclerView()
        setObserve()
        load()
    }

    fun setRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = SearchItemAdapter {  }
        binding.recyclerView.setHasFixedSize(true)

    }

    fun setObserve(){
        searchViewModel.items.observe(viewLifecycleOwner){ items ->
            ((binding.recyclerView.adapter) as ListAdapter<*, *>).submitList(items as List<Nothing>?)
        }
    }

    fun load(){
        searchViewModel.popularList()
    }
}