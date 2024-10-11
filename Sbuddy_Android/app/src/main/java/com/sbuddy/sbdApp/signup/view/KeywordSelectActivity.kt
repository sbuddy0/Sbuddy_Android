package com.sbuddy.sbdApp.signup.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.ActivityKeywordSelectBinding
import com.sbuddy.sbdApp.login.view.LoginActivity
import com.sbuddy.sbdApp.mypage.viewmodel.MypageViewModel
import com.sbuddy.sbdApp.post.adapter.KeywordItemAdapter
import com.sbuddy.sbdApp.post.view.MainActivity
import com.sbuddy.sbdApp.post.viewmodel.PostViewModel
import com.sbuddy.sbdApp.signup.viewmodel.SignUpViewModel
import kotlin.math.sign

class KeywordSelectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKeywordSelectBinding
    private lateinit var signupViewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_keyword_select)
        signupViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        binding.viewModel = signupViewModel
        binding.lifecycleOwner = this
        binding.activity = this
        setRecyclerView()
        setObserve()

    }

    override fun onResume() {
        super.onResume()
        signupViewModel.loadKeywords()
    }

    fun goBefore(){
        finish()
    }

    fun setRecyclerView(){
        binding.oneRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.oneRecyclerview.adapter = KeywordItemAdapter { idxCategory, idxKeyword ->
            signupViewModel.checkTitleKeyword(idxCategory, idxKeyword)
        }
        binding.oneRecyclerview.setHasFixedSize(true)

        binding.twoRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.twoRecyclerview.adapter = KeywordItemAdapter { idxCategory, idxKeyword ->
            signupViewModel.checkSubKeyword(idxCategory, idxKeyword)
        }
        binding.twoRecyclerview.setHasFixedSize(true)
    }

    fun setObserve(){
        signupViewModel.titleKeywords.observe(this) { keywords ->
            (binding.oneRecyclerview.adapter as? KeywordItemAdapter)?.submitList(keywords)
        }

        signupViewModel.subKeywords.observe(this) { keywords ->
            (binding.twoRecyclerview.adapter as? KeywordItemAdapter)?.submitList(keywords)
        }

        signupViewModel.keywordUpdateFinished.observe(this){
            if(it){
                finish()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}