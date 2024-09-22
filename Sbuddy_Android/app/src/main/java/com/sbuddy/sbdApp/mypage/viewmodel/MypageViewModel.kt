package com.sbuddy.sbdApp.mypage.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbuddy.sbdApp.http.Like
import com.sbuddy.sbdApp.mypage.model.MyLike
import com.sbuddy.sbdApp.mypage.model.MypageData
import com.sbuddy.sbdApp.mypage.model.MypageRepository
import com.sbuddy.sbdApp.post.model.PostItem
import com.sbuddy.sbdApp.util.MetaData
import kotlinx.coroutines.launch

class MypageViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MypageRepository = MypageRepository()

    private var _mypageData = MutableLiveData<MypageData>()
    private var _myLikeList = MutableLiveData<List<MyLike>>()
    private var _myWriteList = MutableLiveData<List<MyLike>>()

    private var _buttonIsLike = MutableLiveData<Boolean>(true)
    private var _buttonIsWrite = MutableLiveData<Boolean>(false)

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

    fun like(myLike: MyLike){
        if(myLike.is_like){
            cancelLike(myLike.idx_post)
        }else{
            likePost(myLike.idx_post)
        }
    }

    fun likePost(idx_post: Int){
        Log.w("sbuddyy", "likePost")
        viewModelScope.launch {
            val response = repository.postLike(Like(idx_post, MetaData.idxMember))
            if(response.isSuccessful){
                val map = response.body() as Map<*, *>
                if(map.get("code") == "200"){
                    myLikeList()
                    return@launch
                }
//                _showToast.value = true
            }
        }
    }

    fun cancelLike(idx_post: Int){
        Log.w("sbuddyy", "cancelLike")
        viewModelScope.launch {
            val response = repository.postcancelLike(Like(idx_post, MetaData.idxMember))
            if(response.isSuccessful){
                val map = response.body() as Map<*, *>
                if(map.get("code") == "200"){
                    myLikeList()
                    return@launch
                }
//                _showToast.value = true
            }
        }
    }

    fun delete(myLike: MyLike){
        if(myLike.idx_member == Integer.parseInt(MetaData.idxMember)){
            viewModelScope.launch {
                val response = repository.postDelete(Like(myLike.idx_post, MetaData.idxMember))
                if(response.isSuccessful){
                    val map = response.body() as Map<*, *>
                    if(map.get("code") == "200"){
                        myLikeList()
                        return@launch
                    }
//                    _showToast.value = true
                }
            }
        }
    }

    fun selectLikeBtn(){
        buttonIsLike.value = true
        buttonIsWrite.value = false
    }

    fun selectWriteBtn(){
        buttonIsWrite.value = true
        buttonIsLike.value = false
    }

    val mypageData: MutableLiveData<MypageData>
        get() = _mypageData

    val myLikeList: MutableLiveData<List<MyLike>>
        get() = _myLikeList

    val myWriteList: MutableLiveData<List<MyLike>>
        get() = _myWriteList

    val buttonIsLike: MutableLiveData<Boolean>
        get() = _buttonIsLike

    val buttonIsWrite: MutableLiveData<Boolean>
        get() = _buttonIsWrite

}