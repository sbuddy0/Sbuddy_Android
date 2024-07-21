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


class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.lifecycleOwner = this
        binding.activity = this

        setObserve()
    }

    fun setObserve(){
        loginViewModel.requestPostLogin.observe(this){
            if(it){
                loginViewModel.postLogin(binding.id.text.toString(), binding.password.text.toString())
            }
        }
        loginViewModel.showMainActivity.observe(this){
            if(it){
                // 메인페이지 이동
                println("showMainAcrtivity.observe : ${it}")
                startActivity(Intent(this, MainActivity::class.java))

            }
        }

        loginViewModel.showSignupActivity.observe(this){
            if(it){
                startActivity(Intent(this, SignUpActivity_01::class.java))
            }
        }
        loginViewModel.showFindPwActivity.observe(this){
            if(it){
                startActivity(Intent(this, FindPwdActivity::class.java))
            }
        }

    }

    fun requestLogin(){
        println("requestLogin")
        loginViewModel.requestPostLogin.value = true
    }

    fun signUp(){
        println("signUp")
        loginViewModel.showSignupActivity.value = true
    }
    fun findPwd(){
        println("findPwd")
        loginViewModel.showFindPwActivity.value = true
    }

}