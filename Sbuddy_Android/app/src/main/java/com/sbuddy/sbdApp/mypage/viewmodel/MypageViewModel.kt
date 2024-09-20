package com.sbuddy.sbdApp.mypage.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbuddy.sbdApp.mypage.model.MyLike
import com.sbuddy.sbdApp.mypage.model.MypageData
import com.sbuddy.sbdApp.mypage.model.MypageRepository
import kotlinx.coroutines.launch

class MypageViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MypageRepository = MypageRepository()

    private var _mypageData = MutableLiveData<MypageData>()
    private var _myLikeList = MutableLiveData<List<MyLike>>()
    private var _myWriteList = MutableLiveData<List<MyLike>>()

    fun myDetail(){
        viewModelScope.launch {
            val response = repository.myDetail()
            if(response.isSuccessful){
                _mypageData.value = response.body()?.data
            }
        }
    }

    fun myLikeList(){
        viewModelScope.launch {
            val response = repository.myLikeList()
            if(response.isSuccessful){
                _myLikeList.value = response.body()?.data?.list
                Log.e("myy", "response.body.data.list : " + response.body()?.data?.list)
            }
        }
    }

    fun myWriteList(){
        viewModelScope.launch {
            val response = repository.myWriteList()
            if(response.isSuccessful){
                _myWriteList.value = response.body()?.data?.list
            }
        }
    }

    val mypageData: MutableLiveData<MypageData>
        get() = _mypageData

    val myLikeList: MutableLiveData<List<MyLike>>
        get() = _myLikeList

    val myWriteList: MutableLiveData<List<MyLike>>
        get() = _myWriteList
}