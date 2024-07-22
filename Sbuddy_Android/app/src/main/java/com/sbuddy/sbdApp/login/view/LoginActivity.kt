package com.sbuddy.sbdApp.login.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sbuddy.sbdApp.MainActivity
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.ActivityLoginBinding
import com.sbuddy.sbdApp.login.viewmodel.LoginViewModel
import com.sbuddy.sbdApp.signup.view.SignUpActivity_01
import com.sbuddy.sbdApp.util.ToastMessage


class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =  DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this
        binding.activity = this

        getEmail()
        setObserve()
    }

    fun getEmail(){
        if(intent.extras?.getString("email") != null){
            loginViewModel.id.value = intent.extras?.getString("email")!!
        }
    }

    fun setObserve(){
        // 다음 화면
        loginViewModel.showNextActivity.observe(this){
            if(it){
                // 메인페이지 이동
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        loginViewModel.showToast.observe(this){
            if(it){
                ToastMessage.show(this, "아이디와 비밀번호를 확인해 주세요.")
            }
        }
    }

    fun requestLogin(){
        loginViewModel.postLogin()
    }

    fun signUp(){
        startActivity(Intent(this, SignUpActivity_01::class.java))
    }
    fun findPwd(){
        startActivity(Intent(this, FindPwdActivity::class.java))
    }

}