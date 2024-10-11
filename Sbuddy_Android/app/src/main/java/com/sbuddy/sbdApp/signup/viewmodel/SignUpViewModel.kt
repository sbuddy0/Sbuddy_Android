package com.sbuddy.sbdApp.signup.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbuddy.sbdApp.http.Email
import com.sbuddy.sbdApp.http.EmailAuth
import com.sbuddy.sbdApp.http.KeywordList
import com.sbuddy.sbdApp.http.SignUp
import com.sbuddy.sbdApp.post.model.Keyword
import com.sbuddy.sbdApp.signup.model.SignUpRepository
import com.sbuddy.sbdApp.util.JsonParser
import kotlinx.coroutines.launch

class SignUpViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SignUpRepository = SignUpRepository()

    var authNumber: MutableLiveData<String> = MutableLiveData("")
    var email : MutableLiveData<String> = MutableLiveData("")
    var userName : MutableLiveData<String> = MutableLiveData("")
    var password : MutableLiveData<String> = MutableLiveData("")

    private var _showNextActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    private var _showToast : MutableLiveData<Boolean> = MutableLiveData(false)

    private var grouped:Map<String?, List<Keyword>> = HashMap<String?, List<Keyword>>()

    private var _keyWords = MutableLiveData<List<Keyword>>()
    private var _titleKeywords = MutableLiveData<List<Keyword>>()
    private var _subKeywords = MutableLiveData<List<Keyword>>()
    private var _keywordUpdateFinished = MutableLiveData<Boolean>(false)

    // request
    fun postAuthEmail(){
        viewModelScope.launch {
            val response = repository.postAuthEmail(Email(email.value!!))

            if (response.isSuccessful) {
                val map = JsonParser.getJsonData(response.body().toString())
                val code = map.get("code")

                if (code == 200) {
                    showNextActivity.value = true
                    return@launch
                }
            }
            showToast.value = true
        }
    }

    fun postAuthNumber(){
        viewModelScope.launch{
            val response = repository.postAuthNumber(EmailAuth(authNumber.value!!, email.value!!))

            if (response.isSuccessful) {
                val map = JsonParser.getJsonData(response.body().toString())
                val code = map.get("code")

                if (code == 200) {
                    showNextActivity.value = true
                    return@launch
                }
            }
            showToast.value = true
        }
    }

    fun postSignUp(){
        viewModelScope.launch {
            val response = repository.postSignUp(SignUp(userName.value!!, email.value!!, password.value!!))

            if (response.isSuccessful) {
                val map = JsonParser.getJsonData(response.body().toString())
                val code = map.get("code")

                if (code == 200) {
                    showNextActivity.value = true
                    return@launch
                }
            }
            showToast.value = true
        }
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

    fun insertKeyword(){
        // 키워드 배열로 만들기
        val keywords = ArrayList<Int>()

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


//        val str: String = keywords.joinToString(",")

        viewModelScope.launch {
            val response = repository.insertKeyword(keywords.toString())
            if(response.isSuccessful){
                _keywordUpdateFinished.value = true
            }
        }
    }

    val showNextActivity : MutableLiveData<Boolean>
        get() = _showNextActivity

    val showToast : MutableLiveData<Boolean>
        get() = _showToast

    val titleKeywords : MutableLiveData<List<Keyword>>
        get() = _titleKeywords

    val subKeywords : MutableLiveData<List<Keyword>>
        get() = _subKeywords

    val keywordUpdateFinished : MutableLiveData<Boolean>
        get() = _keywordUpdateFinished
}