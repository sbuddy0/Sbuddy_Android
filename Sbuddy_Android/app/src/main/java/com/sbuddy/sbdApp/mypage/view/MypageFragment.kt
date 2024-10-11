package com.sbuddy.sbdApp.mypage.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.sbuddy.sbdApp.post.view.PostDetailActivity

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
        mypageViewModel = ViewModelProvider(this)[MypageViewModel::class.java]
        binding.viewModel = mypageViewModel
        binding.lifecycleOwner = this
        binding.fragment = this

        setRecyclerView()
        setObserve()

        // 초기 UI 설정
        updateLikeButtonUI(mypageViewModel.buttonIsLike.value == true)
        updateWriteButtonUI(mypageViewModel.buttonIsWrite.value == true)
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
                    val intent = Intent(context, MypageReviseActivity::class.java)
                    intent.putExtra("position", myLike.idx_post)
                    startActivity(intent)
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
                    val intent = Intent(context, MypageReviseActivity::class.java)
                    intent.putExtra("idx_post", myLike.idx_post)
                    startActivity(intent)
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

        mypageViewModel.myWriteList.observe(viewLifecycleOwner) {
            ((binding.myWriteRecyclerview.adapter) as ListAdapter<*, *>).submitList(mypageViewModel.myWriteList.value as List<Nothing>?)
        }

        mypageViewModel.buttonIsLike.observe(viewLifecycleOwner) {
//            if (it) {
//                binding.likeBtn.background = ContextCompat.getDrawable(
//                    requireContext(),
//                    R.drawable.button_rounded_corner_rectangle
//                )
//                binding.likeBtn.setTextColor(android.graphics.Color.WHITE)
//                binding.likeRecyclerview.visibility = View.VISIBLE
//            } else {
//                binding.writeBtn.background = ContextCompat.getDrawable(
//                    requireContext(),
//                    R.drawable.button_rounded_corner_rectangle_gray
//                )
//                binding.writeBtn.setTextColor(android.graphics.Color.DKGRAY)
//                binding.likeRecyclerview.visibility = View.GONE
//            }
            updateLikeButtonUI(it)
        }

        mypageViewModel.buttonIsWrite.observe(viewLifecycleOwner) {
//            if (it) {
//                binding.writeBtn.background = ContextCompat.getDrawable(
//                    requireContext(),
//                    R.drawable.button_rounded_corner_rectangle
//                )
//                binding.writeBtn.setTextColor(android.graphics.Color.WHITE)
//                binding.myWriteRecyclerview.visibility = View.VISIBLE
//            } else {
//                binding.likeBtn.background = ContextCompat.getDrawable(
//                    requireContext(),
//                    R.drawable.button_rounded_corner_rectangle_gray
//                )
//                binding.writeBtn.setTextColor(android.graphics.Color.DKGRAY)
//                binding.myWriteRecyclerview.visibility = View.GONE
//            }
            updateWriteButtonUI(it)
        }
    }

    private fun updateLikeButtonUI(isLiked: Boolean) {
        val backgroundRes = if (isLiked) R.drawable.button_rounded_corner_rectangle else R.drawable.button_rounded_corner_rectangle_gray
        binding.likeBtn.background = ContextCompat.getDrawable(requireContext(), backgroundRes)
        binding.likeBtn.setTextColor(if (isLiked) Color.WHITE else Color.DKGRAY)
        binding.likeRecyclerview.visibility = if (isLiked) View.VISIBLE else View.GONE
    }

    private fun updateWriteButtonUI(isWritten: Boolean) {
        val backgroundRes = if (isWritten) R.drawable.button_rounded_corner_rectangle else R.drawable.button_rounded_corner_rectangle_gray
        binding.writeBtn.background = ContextCompat.getDrawable(requireContext(), backgroundRes)
        binding.writeBtn.setTextColor(if (isWritten) Color.WHITE else Color.DKGRAY)
        binding.myWriteRecyclerview.visibility = if (isWritten) View.VISIBLE else View.GONE
    }

    fun goKeywordActivity(){
        startActivity(Intent(context, KeywordActivity::class.java))
    }
}