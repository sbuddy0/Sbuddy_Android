package com.sbuddy.sbdApp.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sbuddy.sbdApp.MainActivity
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.ActivityLoginBinding
import com.sbuddy.sbdApp.http.RetrofitService
import com.sbuddy.sbdApp.http.SbuddyRetrofitService
import com.sbuddy.sbdApp.http.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    lateinit var loginViewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =  DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.lifecycleOwner = this
        binding.activity = this

        setObserve()
    }

    fun setObserve(){
        var id : String = ""
        var pwd : String = ""
        loginViewModel.id.observe(this){
            id = it
        }
        loginViewModel.password.observe(this){
            pwd = it
        }
        loginViewModel.requestPostLogin.observe(this){
            if(it){
                loginViewModel.postLogin(User(id, pwd))
            }
        }
        loginViewModel.showMainActivity.observe(this){
            if(it){
                // 메인페이지 이동
                startActivity(Intent(this, MainActivity::class.java))

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
    fun findPwd(){
        println("findPwd")
        loginViewModel.showFindPwActivity.value = true
    }

}