package com.sbuddy.sbdApp.login.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbuddy.sbdApp.http.User
import com.sbuddy.sbdApp.login.model.LoginRepository
import com.sbuddy.sbdApp.util.JsonParser
import com.sbuddy.sbdApp.util.ShareData
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application){

    private val repository: LoginRepository = LoginRepository()

    var id : MutableLiveData<String> = MutableLiveData("")
    var password : MutableLiveData<String> = MutableLiveData("")

    var requestPostLogin : MutableLiveData<Boolean> = MutableLiveData(false)
    var showMainActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var showSignupActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var showFindPwActivity : MutableLiveData<Boolean> = MutableLiveData(false)



    // Response
    fun postLogin(id: String, pwd: String){
        viewModelScope.launch {
            val response = repository.postLogin(User(id, pwd))

            if(response.isSuccessful){
                showMainActivity.value = true // main에 상태를 알려줌
                val map = JsonParser.getJsonData(response.body().toString())
                val data = map.get("data") as HashMap<*, *>
                ShareData.setStringData(getApplication(), ShareData.LOGIN, ShareData.LOGIN_SESSION, data.get("token") as String)
            }else{
                Log.d("sbuddyy", "로그인 실패 : ")
            }
        }
    }


}