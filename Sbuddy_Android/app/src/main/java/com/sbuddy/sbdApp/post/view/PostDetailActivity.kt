package com.sbuddy.sbdApp.post.view

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.ActivityPostDetailBinding
import com.sbuddy.sbdApp.post.viewmodel.PostViewModel

class PostDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostDetailBinding
    private lateinit var postViewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail)
        postViewModel = ViewModelProvider(this)[PostViewModel::class.java]
        binding.viewModel = postViewModel
        binding.lifecycleOwner = this
        binding.activity = this

        setObserve()
        detail()
    }

    fun setObserve(){
        postViewModel.detail
    }

    fun detail(){
        // postviewModel의 아이템에서 현재 idx 가져옴\
        val intent = getIntent()
        val idxPost = intent.getIntExtra("idx_post", 0)
        postViewModel.loadItems{
            postViewModel.setItem(idxPost)
            postViewModel.detail(idxPost)
            setKeyword()
        }
    }

    fun setKeyword(){
        // 키워드만큼 화면에 뿌려주기
        val seletedItem = postViewModel.item.value!!
        Log.w("keywordd", "selectedItem : " + seletedItem)
        for (i in 0 until seletedItem.keyword_list.size){
            val textView = TextView(this)

            // 레이아웃 파라미터 설정
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(10, 10, 10, 10)  // 마진 추가
            textView.layoutParams = layoutParams

            textView.setPadding(40, 25, 40, 25)
            textView.setText(seletedItem.keyword_list.get(i).description)
            textView.setTextColor(android.graphics.Color.parseColor("#998DFF"))
            textView.background = ContextCompat.getDrawable(this, R.drawable.keyword_rounded_corner)
            binding.keywordLayout.addView(textView)
        }
    }

    fun goBefore(){
        finish()
    }
}