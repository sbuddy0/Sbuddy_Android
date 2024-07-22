package com.sbuddy.sbdApp.signup.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.sbuddy.sbdApp.R
import com.sbuddy.sbdApp.databinding.ActivitySignUp02Binding
import com.sbuddy.sbdApp.signup.viewmodel.SignUpViewModel
import com.sbuddy.sbdApp.util.ToastMessage

class SignUpActivity_02 : AppCompatActivity() {
    private lateinit var binding : ActivitySignUp02Binding
    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var email: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up_02)
        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        binding.viewModel = signUpViewModel
        binding.lifecycleOwner = this
        binding.activity = this

        getEmail() // viewmodel 데이터가 싹 리셋되기 때문에 email은 전 acitivity에서 받아와야함
        setObserve()
    }

    fun setObserve() {
        signUpViewModel.showBeforeActivity.observe(this){
            if(it){
                startActivity(Intent(this, SignUpActivity_01::class.java))
            }
        }

        signUpViewModel.requestPostAuthNumber.observe(this){
            if(it){
                signUpViewModel.postAuthNumber()
            }
        }

        signUpViewModel.showToast.observe(this){
            if(it){
                ToastMessage.show(this, "인증번호를 다시 입력하세요.")
            }
        }

        signUpViewModel.showNextActivity.observe(this){
            if(it){
                val intent = Intent(this, SignUpActivity_03::class.java)
                intent.putExtra("email", signUpViewModel.email.value!!)
                startActivity(intent)
            }
        }
    }
    fun requestAuthNumber(){
        signUpViewModel.requestPostAuthNumber.value = true
    }

    fun getEmail(){
        signUpViewModel.email.value = this.intent.extras?.getString("email")!!
    }

    fun goBackStep(){
        signUpViewModel.showBeforeActivity.value = true
    }
}