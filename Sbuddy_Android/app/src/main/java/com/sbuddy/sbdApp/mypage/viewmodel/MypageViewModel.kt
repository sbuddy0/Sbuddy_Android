package com.sbuddy.sbdApp.mypage.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbuddy.sbdApp.mypage.model.MypageData
import com.sbuddy.sbdApp.mypage.model.MypageRepository
import kotlinx.coroutines.launch

class MypageViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MypageRepository = MypageRepository()

    private var _mypageData = MutableLiveData<MypageData>()
    fun myDetail(){
        viewModelScope.launch {
            val response = repository.myDetail()
            if(response.isSuccessful){
                _mypageData.value = response.body()?.data
            }
        }
    }

    val mypageData: MutableLiveData<MypageData>
        get() = _mypageData
}