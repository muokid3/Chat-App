package com.dm.berxley.chatapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.berxley.chatapp.data.ChatMessage
import com.dm.berxley.chatapp.data.MessageRepository
import com.dm.berxley.chatapp.data.Result
import com.dm.berxley.chatapp.data.User
import com.dm.berxley.chatapp.data.UserRepository
import com.dm.berxley.chatapp.utils.Injection
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MessageViewModel: ViewModel() {
    private val messageRepository: MessageRepository
    private val userRepository: UserRepository

    init {
        messageRepository = MessageRepository(Injection.instance())
        userRepository = UserRepository(FirebaseAuth.getInstance(), Injection.instance())
        loadCurrentUser()
    }

    private val _messages = MutableLiveData<List<ChatMessage>>()
    val messages: LiveData<List<ChatMessage>> get() = _messages

    private val _roomId = MutableLiveData<String>()
    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser


    private fun loadCurrentUser() {
        viewModelScope.launch {
            when(val result = userRepository.getCurrentUser()){
                is Result.Success<*> -> _currentUser.value = (result.data as User?)!!
                is Result.Error<*> -> {
                    //handle error here
                }
                else -> {
                    //fatal error occured
                }
            }
        }
    }

    fun loadMessages(){
        viewModelScope.launch {
            if (_roomId != null){
                messageRepository.getChatMesages(_roomId.value.toString()).collect{
                    _messages.value = it
                }
            }
        }
    }

    fun sendMessage(text: String){
        if (_currentUser.value != null){
            val message = ChatMessage(
                message = text,
                senderFirstName = _currentUser.value!!.firstName,
                senderId = _currentUser.value!!.email,
                timestamp = System.currentTimeMillis(),
                isSentByCurrentUser = true)

            viewModelScope.launch {
                when(messageRepository.sendMessage(_roomId.value.toString(), message)){
                    is Result.Success -> Unit
                    is Result.Error<*> -> {
                        //handle error here
                    }
                }
            }
        }
    }

    fun setRoomId(roomId: String){
       _roomId.value = roomId
       loadMessages()
    }

}