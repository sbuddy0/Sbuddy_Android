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
import com.sbuddy.sbdApp.http.Like
import com.sbuddy.sbdApp.http.Post
import com.sbuddy.sbdApp.http.Search
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

    private var _items = MutableLiveData<List<PostItem>>()
    private var _showToast : MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        loadItems()
    }

    fun loadItems(){
        viewModelScope.launch {
            val response = repository.postList(Search(MetaData.idxMember, ""))
            if(response.isSuccessful){
                if(response.body()?.code == "200"){
                    Log.w("sbuddyy", "data.list : " + response.body()!!.data.list)
                    _items.value = response.body()!!.data.list
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
                    loadItems()
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
                    loadItems()
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
                        loadItems()
                        return@launch
                    }
                    _showToast.value = true
                }
            }
        }
    }

    fun post(img: MultipartBody.Part, post: Post){
        viewModelScope.launch {
            val response = repository.post(img, post)
            if(response.isSuccessful){
                Log.d("sbuddyy", "게시성공")
            }
        }
    }


    fun uploadImageToServer(file: File){
        val array = ArrayList<Int>()
        array.add(1)
        val post = Post("제목", "ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ", array)
        post(UploadUtil.prepareFilePart("file", file), post)
    }

    val items: LiveData<List<PostItem>>
        get() = _items

    val showToast : MutableLiveData<Boolean>
        get() = _showToast

}