package com.sbuddy.sbdApp.search.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbuddy.sbdApp.post.model.PostItem
import com.sbuddy.sbdApp.search.model.SearchItem
import com.sbuddy.sbdApp.search.model.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): AndroidViewModel(application) {
    private val repository: SearchRepository = SearchRepository()

    private var _items: MutableLiveData<List<SearchItem>> = MutableLiveData<List<SearchItem>>()
    private var _showToast : MutableLiveData<Boolean> = MutableLiveData(false)

    fun popularList(){
        viewModelScope.launch {
            val response = repository.popularList()
            Log.w("searchh", "response : " + response.body())
            if(response.isSuccessful){
                if(response.body()?.code == "200"){
                    _items.value = response.body()!!.data.list
                    return@launch
                }
                _showToast.value = true
            }
        }
    }

    val items : MutableLiveData<List<SearchItem>>
        get() = _items
}