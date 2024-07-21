package com.sbuddy.sbdApp.signup.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.ActivitySignUp01Binding
import com.sbuddy.sbdApp.login.view.LoginActivity
import com.sbuddy.sbdApp.signup.viewmodel.SignUpViewModel

class SignUpActivity_01 : AppCompatActivity() {
    private lateinit var binding : ActivitySignUp01Binding
    private lateinit var signUpViewModel: SignUpViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_01)
        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        binding.lifecycleOwner = this
        binding.activity = this

        setObserve()
    }

    fun setObserve(){
        signUpViewModel.showBeforeActivity.observe(this){
            if(it){
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        signUpViewModel.requestPostAuthEmail.observe(this){
            if(it){
                signUpViewModel.postAuthEmail(binding.email.text.toString())
            }
        }
        signUpViewModel.showNextActivity.observe(this){
            if(it){
                startActivity(Intent(this, SignUpActivity_02::class.java))
            }
        }
    }

    fun goBeforeStep(){
        signUpViewModel.showBeforeActivity.value = true
    }

    fun requestAuthEmail(){
        signUpViewModel.requestPostAuthEmail.value = true
    }
}