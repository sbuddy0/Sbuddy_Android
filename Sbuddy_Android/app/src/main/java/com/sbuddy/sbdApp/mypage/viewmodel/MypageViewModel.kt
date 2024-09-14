package com.sbuddy.sbdApp.mypage.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.sbuddy.sbdApp.mypage.model.MypageRepository
import kotlinx.coroutines.launch

class MypageViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: MypageRepository = MypageRepository()

    fun myDetail(){
        viewModelScope.launch {
            val response = repository.myDetail()
            if(response.isSuccessful){
                Log.e("myy", "response.body : " + response.body())
            }
        }
    }
}