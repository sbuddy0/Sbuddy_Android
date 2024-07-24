package com.sbuddy.sbdApp.post.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.sbuddy.sbdApp.http.Search
import com.sbuddy.sbdApp.post.model.PostItem
import com.sbuddy.sbdApp.post.model.PostRepository
import com.sbuddy.sbdApp.post.model.PostResponse
import com.sbuddy.sbdApp.util.JsonParser
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepository()

    private var _items = MutableLiveData<List<PostItem>>()
    private var _showToast : MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        loadItems()
    }

    fun loadItems(){
        viewModelScope.launch {

            val response = repository.postList(Search(""))
            if(response.isSuccessful){
                if(response.body()?.code == "OK"){
                    _items.value = response.body()!!.data.list
                    return@launch
                }
                _showToast.value = true
            }
        }
    }

    val items: LiveData<List<PostItem>>
        get() = _items

    val showToast : MutableLiveData<Boolean>
        get() = _showToast

}