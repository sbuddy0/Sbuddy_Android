package com.sbuddy.sbdApp.login

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sbuddy.sbdApp.http.User
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel(){

    private val repository: LoginRepository = LoginRepository()

    var id : MutableLiveData<String> = MutableLiveData("")
    var password : MutableLiveData<String> = MutableLiveData("")

    var requestPostLogin : MutableLiveData<Boolean> = MutableLiveData(false)
    var showMainActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var showFindPwActivity : MutableLiveData<Boolean> = MutableLiveData(false)


    // Response 변수
    val loginResponse = MutableLiveData<String>()


    // Response
    fun postLogin(user: User){
        viewModelScope.launch {
            val response = repository.postLogin(user)

            if(response.isSuccessful){
                showMainActivity.value = true
            }else{
                Log.d("sbuddyy", "로그인 실패 : ")
            }
        }
    }


}