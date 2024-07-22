package com.sbuddy.sbdApp.signup.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbuddy.sbdApp.http.Email
import com.sbuddy.sbdApp.http.EmailAuth
import com.sbuddy.sbdApp.http.SignUp
import com.sbuddy.sbdApp.signup.model.SignUpRepository
import com.sbuddy.sbdApp.util.JsonParser
import kotlinx.coroutines.launch

class SignUpViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SignUpRepository = SignUpRepository()

    var authNumber: MutableLiveData<String> = MutableLiveData("")
    var email : MutableLiveData<String> = MutableLiveData("")
    var userName : MutableLiveData<String> = MutableLiveData("")
    var password : MutableLiveData<String> = MutableLiveData("")

    var showBeforeActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var showNextActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var showToast : MutableLiveData<Boolean> = MutableLiveData(false)

    var requestPostAuthNumber : MutableLiveData<Boolean> = MutableLiveData(false)
    var requestPostAuthEmail : MutableLiveData<Boolean> = MutableLiveData(false)
    var requestSignUp : MutableLiveData<Boolean> = MutableLiveData(false)

    // request
    fun postAuthEmail(){
        viewModelScope.launch {
            val response = repository.postAuthEmail(Email(email.value!!))

            if (response.isSuccessful) {
                val map = JsonParser.getJsonData(response.body().toString())
                val code = map.get("code")

                if (code == "OK") {
                    showNextActivity.value = true
                    return@launch
                }
            }
            showToast.value = true
        }
    }

    fun postAuthNumber(){
        viewModelScope.launch{
            val response = repository.postAuthNumber(EmailAuth(authNumber.value!!, email.value!!))

            if (response.isSuccessful) {
                val map = JsonParser.getJsonData(response.body().toString())
                val code = map.get("code")

                if (code == "OK") {
                    showNextActivity.value = true
                    return@launch
                }
            }
            showToast.value = true
        }
    }

    fun postSignUp(){
        viewModelScope.launch {
            val response = repository.postSignUp(SignUp(userName.value!!, email.value!!, password.value!!))

            if (response.isSuccessful) {
                val map = JsonParser.getJsonData(response.body().toString())
                val code = map.get("code")

                if (code == "OK") {
                    showNextActivity.value = true
                    return@launch
                }
            }
            showToast.value = true
        }
    }
}