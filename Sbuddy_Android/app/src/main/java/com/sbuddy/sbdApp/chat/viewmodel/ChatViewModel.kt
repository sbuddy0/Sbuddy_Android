package com.sbuddy.sbdApp.chat.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sbuddy.sbdApp.chat.model.Chat
import com.sbuddy.sbdApp.chat.model.ChatRepository
import kotlinx.coroutines.launch

class ChatViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ChatRepository = ChatRepository()

    private var _receivedChats = MutableLiveData<List<Chat>>()
    private var _sendChats = MutableLiveData<List<Chat>>()
    private var _buttonIsReceived = MutableLiveData<Boolean>(true)
    private var _buttonIsSend = MutableLiveData<Boolean>(false)

    fun receivedChatList(type: String){
        viewModelScope.launch {
            viewModelScope.launch {
                val response = repository.messageList(type)
                Log.w("chatt", "chatt response : " + response.body())
                if(response.isSuccessful){
                    if(response.code() == 200){
                        _receivedChats.value = response.body()?.data?.list
                    }
                }
            }
        }
    }

    fun sendChatList(type: String){
        viewModelScope.launch {
            val response = repository.messageList(type)
            if(response.isSuccessful){
                if(response.isSuccessful){
                    if(response.code() == 200){
                        _sendChats.value = response.body()?.data?.list
                    }
                }
            }
        }
    }

    fun selectReceivedBtn(){
        buttonIsReceived.value = true
        buttonIsSend.value = false
    }

    fun selectSendBtn(){
        buttonIsSend.value = true
        buttonIsReceived.value = false
    }

    val receivedChats: MutableLiveData<List<Chat>>
        get() = _receivedChats

    val sendChats: MutableLiveData<List<Chat>>
        get() = _sendChats

    val buttonIsReceived: MutableLiveData<Boolean>
        get() = _buttonIsReceived

    val buttonIsSend: MutableLiveData<Boolean>
        get() = _buttonIsSend

}