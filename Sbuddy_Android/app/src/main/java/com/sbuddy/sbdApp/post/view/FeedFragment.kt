package com.sbuddy.sbdApp.post.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import com.sbuddy.sbdApp.databinding.FragmentFeedBinding
import com.sbuddy.sbdApp.post.adapter.PostItemAdapter
import com.sbuddy.sbdApp.post.listener.PostItemClickListener
import com.sbuddy.sbdApp.post.model.PostItem
import com.sbuddy.sbdApp.post.viewmodel.PostViewModel
import com.sbuddy.sbdApp.util.MetaData
import com.sbuddy.sbdApp.util.UploadUtil

class FeedFragment : Fragment(), PostItemClickListener {
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
        binding.fragment = this
        setRecyclerView()
        setObserve()
    }

    override fun onResume() {
        super.onResume()
        postViewModel.loadItems{}
    }

    fun setRecyclerView(){
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = PostItemAdapter(this)
        binding.recyclerView.setHasFixedSize(true)
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

    fun goWriteActivity(){
        startActivity(Intent(context, PostWriteActivity::class.java))
    }
    override fun onHeartIconClicked(postItem: PostItem) {
        postViewModel.like(postItem)
    }

    override fun onDeleteClicked(postItem: PostItem) {
        Log.w("sbuddyy", "onDeleteClicked")
        postViewModel.delete(postItem)
    }

    override fun onReviseClicked(idxMember : Int, position: Int) {
        if(idxMember.toString() == MetaData.idxMember){
            val intent = Intent(context, PostReviseActivity::class.java)
            intent.putExtra("position", position)
            startActivity(intent)
        }
    }

    override fun onItemClicked(postItem: PostItem) {
        val intent = Intent(context, PostDetailActivity::class.java)
        intent.putExtra("idx_post", postItem.idx_post)
        startActivity(intent)
    }
}