package com.sbuddy.sbdApp.mypage.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbuddy.sbdApp.http.Detail
import com.sbuddy.sbdApp.http.Like
import com.sbuddy.sbdApp.http.Update
import com.sbuddy.sbdApp.mypage.model.MyLike
import com.sbuddy.sbdApp.mypage.model.MypageData
import com.sbuddy.sbdApp.mypage.model.MypageRepository
import com.sbuddy.sbdApp.post.model.Keyword
import com.sbuddy.sbdApp.post.model.PostDetailData
import com.sbuddy.sbdApp.post.model.PostItem
import com.sbuddy.sbdApp.util.MetaData
import com.sbuddy.sbdApp.util.UploadUtil
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File

class MypageViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MypageRepository = MypageRepository()

    private var _mypageData = MutableLiveData<MypageData>()
    private var _myLikeList = MutableLiveData<List<MyLike>>()
    private var _myWriteList = MutableLiveData<List<MyLike>>()

    private var _buttonIsLike = MutableLiveData<Boolean>(true)
    private var _buttonIsWrite = MutableLiveData<Boolean>(false)

    private var grouped:Map<String?, List<Keyword>> = HashMap<String?, List<Keyword>>()

    private var _keyWords = MutableLiveData<List<Keyword>>()
    private var _titleKeywords = MutableLiveData<List<Keyword>>()
    private var _subKeywords = MutableLiveData<List<Keyword>>()

    private var _keywordUpdateFinished = MutableLiveData<Boolean>(false)

    private var _showNextActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    private var _showToast : MutableLiveData<Boolean> = MutableLiveData(false)
    private var _updateFinished: MutableLiveData<Boolean> = MutableLiveData(false)


    private var _selectedImageUri = MutableLiveData<Uri>()
    private var _detail = MutableLiveData<PostDetailData>()

    init {
        loadKeywords()
    }

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
                        myWriteList()
                        return@launch
                    }
//                    _showToast.value = true
                }
            }
        }
    }

    fun revise(file: File?, title: String, content: String, position: Int){
        try {
            viewModelScope.launch {
                // 키워드 배열로 만들기
                val keywords = ArrayList<Int>()

                for(i in 0 until _titleKeywords.value!!.size){
                    if(_titleKeywords.value!!.get(i).isChecked){
                        keywords.add(_titleKeywords.value!!.get(i).idx_keyword)
                    }
                }
                for(i in 0 until _subKeywords.value!!.size){
                    if(_subKeywords.value!!.get(i).isChecked){
                        keywords.add(_subKeywords.value!!.get(i).idx_keyword)
                    }
                }
                val post = Update(position, title, content, keywords)

                var part : MultipartBody.Part? = null
                if( file != null){
                    part = UploadUtil.prepareFilePart("file", file)
                }
                val response = repository.revise(part, post)
                if(response.isSuccessful){
                    val map = response.body() as Map<*, *>
                    if(map.get("code") == "200"){
                        Log.d("sbuddyy", "수정성공")
                        myLikeList()
                        myWriteList()
                        _updateFinished.value = true
                        return@launch
                    }
                }
//                _showToast.value = true
            }
        }catch (e: Exception){
//            _showToast.value = true
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

    fun loadKeywords(){
        if(_keyWords.value.isNullOrEmpty()){
            viewModelScope.launch {
                val response = repository.keywordList()
                if(response.isSuccessful){
                    if(response.body()?.code == 200){
                        _keyWords.value = response.body()!!.data.list
                        grouped = (_keyWords.value as List<Keyword>).groupBy { it.idx_category }

                        _titleKeywords.value = grouped[null] ?: listOf()
                        val first = _titleKeywords.value!!.get(0)
                        first.isChecked = true
                        _subKeywords.value = grouped.get(first.idx_keyword.toString())
                        Log.d("nowww", "_titleKeywords : " + _titleKeywords.value)
                        Log.d("nowww", "subKeyword : " + _subKeywords.value)

                    }
                }
            }
        }
    }

    fun checkTitleKeyword(idxCategory:String, idxKeyword: String){
        titleCheck(idxKeyword)
        _subKeywords.value = grouped.get(idxKeyword)
    }

    fun checkSubKeyword(idxCategory:String, idxKeyword: String){
        subCheck(idxKeyword)
    }

    fun titleCheck(idxKeyword: String) {
        val updatedList = _titleKeywords.value?.map {
            if (it.idx_keyword.toString() == idxKeyword) {
                it.copy(isChecked = !it.isChecked)
            } else {
                it
            }
        }
        _titleKeywords.postValue(updatedList)
        _subKeywords.postValue(grouped[idxKeyword])
    }

    fun subCheck(idxKeyword: String) {
        val updatedList = _subKeywords.value?.map {
            if (it.idx_keyword.toString() == idxKeyword) {
                it.copy(isChecked = !it.isChecked)
            } else {
                it
            }
        }
        _subKeywords.postValue(updatedList)
    }

    fun updateKeyword(){
        // 키워드 배열로 만들기
        val keywords = ArrayList<Int>()

        Log.d("noww", "titleKeywords.value : " + titleKeywords.value)
        Log.d("noww", "subKeywords.value : " + subKeywords.value)

        for(i in 0 until _titleKeywords.value!!.size){
            if(_titleKeywords.value!!.get(i).isChecked){
                keywords.add(_titleKeywords.value!!.get(i).idx_keyword)
            }
        }

        if(!_subKeywords.value.isNullOrEmpty()){
            for(i in 0 until _subKeywords.value!!.size){
                if(_subKeywords.value!!.get(i).isChecked){
                    keywords.add(_subKeywords.value!!.get(i).idx_keyword)
                }
            }
        }
        Log.d("noww", "keywords: " + keywords)

        val str: String = keywords.joinToString(",")

        viewModelScope.launch {
            val response = repository.upadateKeyword(str)
            if(response.isSuccessful){
                _keywordUpdateFinished.value = true
            }
        }

    }

    fun setSelectedImageUri(uri : Uri){
        _selectedImageUri.value = uri
    }

    fun detail(idxPost: Int){
        viewModelScope.launch {
            val response = repository.detail(Detail(idxPost))
            if(response.isSuccessful){
                Log.d("detaill",  "디테일 결과 : " + response.body())
                _detail.value = response.body()!!.data.data

                val updatedTitleKeywords = titleKeywords.value?.map { keyword ->
                    keyword.copy(isChecked = detail.value!!.keyword.any { it.idx_keyword == keyword.idx_keyword })
                }
                titleKeywords.postValue(updatedTitleKeywords)

                val updatedSubKeywords = subKeywords.value?.map { keyword ->
                    keyword.copy(isChecked = detail.value!!.keyword.any { it.idx_keyword == keyword.idx_keyword })
                }
                subKeywords.postValue(updatedSubKeywords)
            }
        }
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

    val titleKeywords : MutableLiveData<List<Keyword>>
        get() = _titleKeywords

    val subKeywords : MutableLiveData<List<Keyword>>
        get() = _subKeywords

    val keywordUpdateFinished : MutableLiveData<Boolean>
        get() = _keywordUpdateFinished

    val updateFinished : MutableLiveData<Boolean>
        get() = _updateFinished

    val selectedImageUri : MutableLiveData<Uri>
        get() = _selectedImageUri

    val showNextActivity : MutableLiveData<Boolean>
        get() = _showNextActivity

    val detail : MutableLiveData<PostDetailData>
        get() = _detail

    val showToast : MutableLiveData<Boolean>
        get() = _showToast
}