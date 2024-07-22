package com.sbuddy.sbdApp.login.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.ActivityFindPwdBinding
import com.sbuddy.sbdApp.databinding.ActivityLoginBinding
import com.sbuddy.sbdApp.login.viewmodel.LoginViewModel
import com.sbuddy.sbdApp.util.ToastMessage

class FindPwdActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFindPwdBinding
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_pwd)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this
        binding.activity = this

        setObserve()
    }

    fun setObserve(){
        loginViewModel.showNextActivity.observe(this){
            if(it){
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("email", loginViewModel.id.value)
                startActivity(intent)
            }
        }

        loginViewModel.showToast.observe(this){
            if(it){
                ToastMessage.show(this, "이메일을 다시 입력해 주세요")
            }
        }
    }

    fun requestFindPwd(){
        loginViewModel.postFindPwd()
    }

    fun goBackStep(){
        startActivity(Intent(this, LoginActivity::class.java))
    }
}