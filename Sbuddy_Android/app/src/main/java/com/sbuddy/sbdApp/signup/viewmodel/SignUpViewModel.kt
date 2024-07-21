package com.sbuddy.sbdApp.signup.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbuddy.sbdApp.login.model.LoginRepository
import com.sbuddy.sbdApp.signup.model.SignUpRepository
import kotlinx.coroutines.launch

class SignUpViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SignUpRepository = SignUpRepository()

    var email : MutableLiveData<String> = MutableLiveData("")

    var showBeforeActivity : MutableLiveData<Boolean> = MutableLiveData(false)

    var requestPostAuthEmail : MutableLiveData<Boolean> = MutableLiveData(false)
    var showNextActivity : MutableLiveData<Boolean> = MutableLiveData(false)


    // request
    fun postAuthEmail(email: String){
        viewModelScope.launch {
            val response = repository.postAuthEmail(email)

            if(response.isSuccessful){
                showNextActivity.value = true
                Log.w("sbudyy", "response : $response")
            }else{
                Log.w("sbuddyy", "response : $response")
            }
        }
    }
}