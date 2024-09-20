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
import com.sbuddy.sbdApp.post.adapter.PostItemAdapter
import com.sbuddy.sbdApp.post.listener.PostItemClickListener
import com.sbuddy.sbdApp.post.model.PostItem

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
        mypageViewModel.myLikeList()
        mypageViewModel.myWriteList()
    }

    fun setKeywordRecyclerView(){
        binding.keywordRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.keywordRecyclerview.adapter = KeywordItemAdapter()
        binding.keywordRecyclerview.setHasFixedSize(true)


        // 어댑터 달기
        binding.likeRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.likeRecyclerview.adapter = com.sbuddy.sbdApp.mypage.adapter.PostItemAdapter(
            object : PostItemClickListener{
                override fun onHeartIconClicked(postItem: PostItem) {
                    TODO("Not yet implemented")
                }

                override fun onDeleteClicked(postItem: PostItem) {
                    TODO("Not yet implemented")
                }

                override fun onReviseClicked(postItem: PostItem) {
                    TODO("Not yet implemented")
                }

                override fun onItemClicked(postItem: PostItem) {
                    TODO("Not yet implemented")
                }

            }
        )
        binding.likeRecyclerview.setHasFixedSize(true)
    }

    fun setObserve(){
        mypageViewModel.mypageData.observe(viewLifecycleOwner){
            ((binding.keywordRecyclerview.adapter) as ListAdapter<*, *>).submitList(mypageViewModel.mypageData.value?.keyword as List<Nothing>?)
        }

        mypageViewModel.myLikeList.observe(viewLifecycleOwner){
            ((binding.likeRecyclerview.adapter) as ListAdapter<*, *>).submitList(mypageViewModel.myLikeList.value as List<Nothing>?)
        }
    }
}