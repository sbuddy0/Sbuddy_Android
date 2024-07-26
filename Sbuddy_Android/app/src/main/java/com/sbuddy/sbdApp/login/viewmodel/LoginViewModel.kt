package com.sbuddy.sbdApp.login.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbuddy.sbdApp.http.Email
import com.sbuddy.sbdApp.http.User
import com.sbuddy.sbdApp.login.model.LoginRepository
import com.sbuddy.sbdApp.util.JsonParser
import com.sbuddy.sbdApp.util.MetaData
import com.sbuddy.sbdApp.util.ShareData
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application){

    private val repository: LoginRepository = LoginRepository()

    var id : MutableLiveData<String> = MutableLiveData("")
    var password : MutableLiveData<String> = MutableLiveData("")

    private var _showNextActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    private var _showToast : MutableLiveData<Boolean> = MutableLiveData(false)

    // Response
    fun postLogin(){
        viewModelScope.launch {
            try {
                val response = repository.postLogin(User(id.value!!, password.value!!))

                if(response.isSuccessful){
                    val map = JsonParser.getJsonData(response.body().toString())
                    val code = map.get("code")

                    if(code == "200"){
                        showNextActivity.value = true // main에 상태를 알려줌
                        val data = map.get("data") as HashMap<*, *>
                        MetaData.token = data.get("token") as String
                        MetaData.idxMember = data.get("idx_member") as String
                        ShareData.setStringData(getApplication(), ShareData.LOGIN, ShareData.LOGIN_SESSION, data.get("token") as String)
                        ShareData.setStringData(getApplication(), ShareData.LOGIN, ShareData.LOGIN_IDX_MEMBER, data.get("idx_member") as String)
                        return@launch
                    }
                    _showToast.value = true
                }
            }catch (e: Exception){
                _showToast.value = true
            }

        }
    }

    fun postFindPwd() {
        viewModelScope.launch {
            val response = repository.postFindPwd(Email(id.value!!))
            if(response.isSuccessful){
                val map = JsonParser.getJsonData(response.body().toString())
                val code = map.get("code")

                if(code == "OK"){
                    showNextActivity.value = true // main에 상태를 알려줌
                    return@launch
                }
                _showToast.value = true
            }
        }
    }

    // viewmodel의 요소를 ui가 observe할 수 있게 열어줌
    val showNextActivity : MutableLiveData<Boolean>
        get() = _showNextActivity

    val showToast : MutableLiveData<Boolean>
        get() = _showToast
}