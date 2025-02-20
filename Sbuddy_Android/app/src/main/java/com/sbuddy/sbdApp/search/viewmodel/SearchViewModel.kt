package com.sbuddy.sbdApp.search.viewmodel

import android.app.Application
import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbuddy.sbdApp.post.listener.LoadListener
import com.sbuddy.sbdApp.post.model.Keyword
import com.sbuddy.sbdApp.post.model.PostItem
import com.sbuddy.sbdApp.search.model.SearchItem
import com.sbuddy.sbdApp.search.model.SearchRecent
import com.sbuddy.sbdApp.search.model.SearchRepository
import com.sbuddy.sbdApp.search.model.SearchText
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): AndroidViewModel(application) {
    private val repository: SearchRepository = SearchRepository()

    private var _items: MutableLiveData<List<SearchItem>> = MutableLiveData<List<SearchItem>>()
    private var _keyWords = MutableLiveData<List<Keyword>>()
    private var grouped:Map<String?, List<Keyword>> = HashMap<String?, List<Keyword>>()
    private var _titleKeywords = MutableLiveData<List<Keyword>>()
    private var _subKeywords = MutableLiveData<List<Keyword>>()

    private var _resultList = MutableLiveData<List<SearchText>>()

    private var _searchRecentList = MutableLiveData<List<SearchRecent>>()
    private var _showToast : MutableLiveData<Boolean> = MutableLiveData(false)

    private var _recentIsNull : MutableLiveData<Boolean> = MutableLiveData(false)
    private var _searchTextIsNull : MutableLiveData<Boolean> = MutableLiveData(false)

    init {

    }

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

    fun loadKeywords(listener: LoadListener){
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

                        var all  = ArrayList<Keyword>()
                        var idx:Int
                        for (i in 0 until _titleKeywords.value!!.size){
                            all.add(_titleKeywords.value!!.get(i))
                            idx = _titleKeywords.value!!.get(i).idx_keyword
                            for(j in 0 until _subKeywords.value!!.size){
                                if(_subKeywords.value!!.get(j).idx_category == idx.toString()){
                                    all.add(_subKeywords.value!!.get(j))
                                }
                            }
                        }

                        _keyWords.value = all
                        Log.e("searchh", "all : " + all)
                        listener.onLoadFinished()

                    }
                }
            }
        }
    }

    fun searchRecentList(){
        viewModelScope.launch {
            val response = repository.searchRecentList()
            if(response.isSuccessful){
                Log.d("searchh", "searchRecentList : " + response.body()!!.data.list.toString())
                val list = response.body()?.data?.list
                searchRecentList.value = list
                Log.w("resultt", "list : " + list)
                if (list.isNullOrEmpty()) {
                    _recentIsNull.value = true
                }else{
                    _recentIsNull.value = false
                }
            }
        }
    }

    fun deleteRecent(idx: Int){
        viewModelScope.launch {
            val response = repository.deleteRecent(idx)
            if(response.isSuccessful){
                searchRecentList()
            }else{
                _showToast.value = true
            }
        }
    }

    fun searchText(text: String){
        viewModelScope.launch {
            val response = repository.searchText(text)
            if(response.isSuccessful){
                Log.w("textt", "텍스트 검색 결과 : " + response.body())
                val list = response.body()?.data?.list
                items.value = list
                Log.w("resultt", "list : " + list)
                if (list.isNullOrEmpty()) {
                    searchTextIsNull.value = true
                }
            }
        }
    }

    val items : MutableLiveData<List<SearchItem>>
        get() = _items

    val keywords : MutableLiveData<List<Keyword>>
        get() = _keyWords

    val searchRecentList : MutableLiveData<List<SearchRecent>>
        get() = _searchRecentList

    val resultList : MutableLiveData<List<SearchText>>
        get() = _resultList

    val recentIsNull : MutableLiveData<Boolean>
        get() = _recentIsNull

    val searchTextIsNull : MutableLiveData<Boolean>
        get() = _searchTextIsNull

    val showToast: MutableLiveData<Boolean>
        get() = _showToast

}