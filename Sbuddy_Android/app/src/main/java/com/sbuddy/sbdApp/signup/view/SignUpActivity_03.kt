package com.sbuddy.sbdApp.signup.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.ActivitySignUp03Binding
import com.sbuddy.sbdApp.login.view.LoginActivity
import com.sbuddy.sbdApp.signup.viewmodel.SignUpViewModel
import com.sbuddy.sbdApp.util.ToastMessage

class SignUpActivity_03 : AppCompatActivity() {
    private lateinit var binding : ActivitySignUp03Binding
    private lateinit var signUpViewModel: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_03)
        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        binding.viewModel = signUpViewModel
        binding.lifecycleOwner = this
        binding.activity = this

        getEmail()
        setObserve()
    }

    fun setObserve(){

        signUpViewModel.showNextActivity.observe(this){
            if(it){
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("email", signUpViewModel.email.value!!)
                startActivity(intent)
            }
        }

        signUpViewModel.showToast.observe(this){
            if(it){
                ToastMessage.show(this, "다시 입력해 주세요.")
            }
        }


    }

    fun getEmail(){
        signUpViewModel.email.value = this.intent.extras?.getString("email")!!
    }

    fun goBackStep(){
        val intent = Intent(this, SignUpActivity_02::class.java)
        intent.putExtra("email", signUpViewModel.email.value)
        startActivity(intent)
    }

    fun requestSignUp(){
        signUpViewModel.postSignUp()
    }
}