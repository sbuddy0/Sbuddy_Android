package com.sbuddy.sbdApp.post.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.sbuddy.sbdApp.http.Detail
import com.sbuddy.sbdApp.http.Like
import com.sbuddy.sbdApp.http.Post
import com.sbuddy.sbdApp.http.Search
import com.sbuddy.sbdApp.post.listener.LoadListener
import com.sbuddy.sbdApp.post.model.Keyword
import com.sbuddy.sbdApp.post.model.PostDetail
import com.sbuddy.sbdApp.post.model.PostItem
import com.sbuddy.sbdApp.post.model.PostRepository
import com.sbuddy.sbdApp.post.model.PostResponse
import com.sbuddy.sbdApp.util.JsonParser
import com.sbuddy.sbdApp.util.MetaData
import com.sbuddy.sbdApp.util.UploadUtil
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepository()

    private var grouped:Map<String?, List<Keyword>> = HashMap<String?, List<Keyword>>()
    private var _items = MutableLiveData<List<PostItem>>()
    private var _item = MutableLiveData<PostItem>()

    private var _keyWords = MutableLiveData<List<Keyword>>()
    private var _titleKeywords = MutableLiveData<List<Keyword>>()
    private var _subKeywords = MutableLiveData<List<Keyword>>()

    private var _selectedImageUri = MutableLiveData<Uri>()
    private var _detail = MutableLiveData<PostDetail>()

    private var _showNextActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    private var _showToast : MutableLiveData<Boolean> = MutableLiveData(false)
    init {
        loadItems {}
        loadKeywords()
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
                        Log.d("keywordd", "subKeyword : " + _subKeywords.value)

                    }
                }
            }
        }
    }
    fun loadItems(listener: LoadListener){
        viewModelScope.launch {
            val response = repository.postList(Search(MetaData.idxMember, ""))
            if(response.isSuccessful){
                if(response.body()?.code == "200"){
                    Log.w("sbuddyy", "data.list : " + response.body()!!.data.list)
                    _items.value = response.body()!!.data.list
                    listener.onLoadFinished()
                    return@launch
                }
                _showToast.value = true
            }
        }
    }

    fun like(postItem: PostItem){
        if(postItem.is_like){
            cancelLike(postItem.idx_post)
        }else{
            likePost(postItem.idx_post)
        }
    }

    fun likePost(idx_post: Int){
        Log.w("sbuddyy", "likePost")
        viewModelScope.launch {
            val response = repository.postLike(Like(idx_post, MetaData.idxMember))
            if(response.isSuccessful){
                val map = response.body() as Map<*, *>
                if(map.get("code") == "200"){
                    loadItems{}
                    return@launch
                }
                _showToast.value = true
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
                    loadItems{}
                    return@launch
                }
                _showToast.value = true
            }
        }
    }

    fun delete(postItem: PostItem){
        if(postItem.idx_member == Integer.parseInt(MetaData.idxMember)){
            viewModelScope.launch {
                val response = repository.postDelete(Like(postItem.idx_post, MetaData.idxMember))
                if(response.isSuccessful){
                    val map = response.body() as Map<*, *>
                    if(map.get("code") == "200"){
                        loadItems{}
                        return@launch
                    }
                    _showToast.value = true
                }
            }
        }
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
    fun checkTitleKeyword(idxCategory:String, idxKeyword: String){
        titleCheck(idxKeyword)
        _subKeywords.value = grouped.get(idxKeyword)
    }

    fun checkSubKeyword(idxCategory:String, idxKeyword: String){
        subCheck(idxKeyword)
    }

    fun post(file: File, title: String, content: String){

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
                val post = Post(title, content, keywords)
                val response = repository.post(UploadUtil.prepareFilePart("file", file), post)
                Log.d("keywordd", "response : " + response.body())
                if(response.isSuccessful){
                    Log.d("sbuddyy", "게시성공")
                    val map = response.body() as Map<*, *>
                    if(map.get("code") == "200"){
                        loadItems{}
                        _showNextActivity.value = true
                        return@launch
                    }
                }
                _showToast.value = true
            }
        }catch (e: Exception){
            _showToast.value = true
        }

    }

    fun setItem(idxPost: Int){
        for(i in 0 until _items.value!!.size){
            if(items.value!!.get(i).idx_post == idxPost){
                _item.value = items.value!!.get(i)
                break
            }
        }
    }

    fun detail(idxPost: Int){
        viewModelScope.launch {
            val response = repository.detail(Detail(idxPost))
            if(response.isSuccessful){
                Log.d("detaill",  "response : " + response.body())
                _detail.value = response.body()!!.data
            }
        }
    }


    fun uploadImageToServer(file: File){
        val array = ArrayList<Int>()
        array.add(1)
//        val post = Post("제목", "ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ", array)
//        post(UploadUtil.prepareFilePart("file", file), post)
    }

    fun setSelectedImageUri(uri : Uri){
        _selectedImageUri.value = uri
    }

    val items: LiveData<List<PostItem>>
        get() = _items

    val keywords : MutableLiveData<List<Keyword>>
        get() = _keyWords

    val titleKeywords : MutableLiveData<List<Keyword>>
        get() = _titleKeywords

    val subKeywords : MutableLiveData<List<Keyword>>
        get() = _subKeywords

    val showToast : MutableLiveData<Boolean>
        get() = _showToast

    val selectedImageUri : MutableLiveData<Uri>
        get() = _selectedImageUri

    val detail : MutableLiveData<PostDetail>
        get() = _detail

    val item : MutableLiveData<PostItem>
        get() = _item

    val showNextActivity : MutableLiveData<Boolean>
        get() = _showNextActivity
}