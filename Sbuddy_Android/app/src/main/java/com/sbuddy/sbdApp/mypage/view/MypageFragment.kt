package com.sbuddy.sbdApp.mypage.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.FragmentMypageBinding
import com.sbuddy.sbdApp.mypage.adapter.KeywordItemAdapter
import com.sbuddy.sbdApp.mypage.viewmodel.MypageViewModel

class MypageFragment : Fragment() {
    private lateinit var binding: FragmentMypageBinding
    private lateinit var mypageViewModel: MypageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mypageViewModel = ViewModelProvider(this)[MypageViewModel::class.java]
        binding.viewModel = mypageViewModel
        binding.lifecycleOwner = this
        binding.fragment = this

        setKeywordRecyclerView()
        setObserve()
    }

    override fun onResume() {
        super.onResume()

        mypageViewModel.myDetail()
    }

    fun setKeywordRecyclerView(){
        binding.keywordRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.keywordRecyclerview.adapter = KeywordItemAdapter()
        binding.keywordRecyclerview.setHasFixedSize(true)
    }

    fun setObserve(){
        mypageViewModel.mypageData.observe(viewLifecycleOwner){
            ((binding.keywordRecyclerview.adapter) as ListAdapter<*, *>).submitList(mypageViewModel.mypageData.value?.keyword as List<Nothing>?)
        }
    }
}