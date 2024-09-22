package com.sbuddy.sbdApp.mypage.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.FragmentMypageBinding
import com.sbuddy.sbdApp.mypage.adapter.KeywordItemAdapter
import com.sbuddy.sbdApp.mypage.model.MyLike
import com.sbuddy.sbdApp.mypage.viewmodel.MypageViewModel
import com.sbuddy.sbdApp.post.adapter.PostItemAdapter
import com.sbuddy.sbdApp.post.listener.PostItemClickListener
import com.sbuddy.sbdApp.post.model.PostItem
import com.sbuddy.sbdApp.post.view.PostDetailActivity
import com.sbuddy.sbdApp.post.viewmodel.PostViewModel

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

        setRecyclerView()
        setObserve()
    }

    override fun onResume() {
        super.onResume()

        mypageViewModel.myDetail()
        mypageViewModel.myLikeList()
        mypageViewModel.myWriteList()
    }

    fun setRecyclerView() {
        // 키워드
        binding.keywordRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.keywordRecyclerview.adapter = KeywordItemAdapter()
        binding.keywordRecyclerview.setHasFixedSize(true)


        // 좋아요 한 글
        binding.likeRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.likeRecyclerview.adapter = com.sbuddy.sbdApp.mypage.adapter.PostItemAdapter(
            object : com.sbuddy.sbdApp.mypage.listener.PostItemClickListener {
                override fun onHeartIconClicked(myLike: MyLike) {
                    mypageViewModel.like(myLike)
                }

                override fun onDeleteClicked(myLike: MyLike) {
                    mypageViewModel.delete(myLike)
                }

                override fun onReviseClicked(myLike: MyLike) {
                    TODO("Not yet implemented")
                }

                override fun onItemClicked(myLike: MyLike) {
                    val intent = Intent(context, PostDetailActivity::class.java)
                    intent.putExtra("idx_post", myLike.idx_post)
                    startActivity(intent)
                }
            })
        binding.likeRecyclerview.setHasFixedSize(true)

        // 내가 쓴 글
        binding.myWriteRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.myWriteRecyclerview.adapter = com.sbuddy.sbdApp.mypage.adapter.PostItemAdapter(
            object : com.sbuddy.sbdApp.mypage.listener.PostItemClickListener {
                override fun onHeartIconClicked(myLike: MyLike) {
                    mypageViewModel.like(myLike)
                }

                override fun onDeleteClicked(myLike: MyLike) {
                    mypageViewModel.delete(myLike)
                }

                override fun onReviseClicked(myLike: MyLike) {
                    TODO("Not yet implemented")
                }

                override fun onItemClicked(myLike: MyLike) {
                    val intent = Intent(context, PostDetailActivity::class.java)
                    intent.putExtra("idx_post", myLike.idx_post)
                    startActivity(intent)
                }

            }
        )
        binding.myWriteRecyclerview.setHasFixedSize(true)
    }

    fun setObserve() {
        mypageViewModel.mypageData.observe(viewLifecycleOwner) {
            ((binding.keywordRecyclerview.adapter) as ListAdapter<*, *>).submitList(mypageViewModel.mypageData.value?.keyword as List<Nothing>?)
        }

        mypageViewModel.myLikeList.observe(viewLifecycleOwner) {
            ((binding.likeRecyclerview.adapter) as ListAdapter<*, *>).submitList(mypageViewModel.myLikeList.value as List<Nothing>?)
        }

        mypageViewModel.myWriteList.observe(viewLifecycleOwner){
            ((binding.myWriteRecyclerview.adapter) as ListAdapter<*, *>).submitList(mypageViewModel.myWriteList.value as List<Nothing>?)
        }

        mypageViewModel.buttonIsLike.observe(viewLifecycleOwner){
            if(it){
                binding.likeSelectBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_rounded_corner_rectangle)
                binding.likeSelectBtn.setTextColor(android.graphics.Color.WHITE)
                binding.likeRecyclerview.visibility = View.VISIBLE
            }else{
                binding.writeSelectBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_rounded_corner_rectangle_gray)
                binding.writeSelectBtn.setTextColor(android.graphics.Color.DKGRAY)
                binding.likeRecyclerview.visibility = View.GONE
            }
        }

        mypageViewModel.buttonIsWrite.observe(viewLifecycleOwner){
            if(it){
                binding.writeSelectBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_rounded_corner_rectangle)
                binding.writeSelectBtn.setTextColor(android.graphics.Color.WHITE)
                binding.myWriteRecyclerview.visibility = View.VISIBLE
            }else{
                binding.likeSelectBtn.background = ContextCompat.getDrawable(requireContext(), R.drawable.button_rounded_corner_rectangle_gray)
                binding.writeSelectBtn.setTextColor(android.graphics.Color.DKGRAY)
                binding.myWriteRecyclerview.visibility = View.GONE
            }
        }
    }
}