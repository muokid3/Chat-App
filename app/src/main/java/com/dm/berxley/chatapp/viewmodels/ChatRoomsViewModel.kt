package com.dm.berxley.chatapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.berxley.chatapp.data.ChatRoom
import com.dm.berxley.chatapp.data.ChatRoomRepository
import com.dm.berxley.chatapp.data.Result
import com.dm.berxley.chatapp.utils.Injection
import kotlinx.coroutines.launch

class ChatRoomsViewModel: ViewModel() {
    private val _rooms = MutableLiveData<List<ChatRoom>>()
    val rooms: LiveData<List<ChatRoom>> get() = _rooms
    private val roomRepository: ChatRoomRepository
    init {
        roomRepository = ChatRoomRepository(Injection.instance())
        loadRooms()
    }

    fun createRoom(name:String){
        viewModelScope.launch {
            val results = roomRepository.createChatRoom(name)
            if (results is Result.Success){
                loadRooms()
            }else{
                //error handling here?
            }
        }
    }
    fun loadRooms() {
        viewModelScope.launch {
            when(val results = roomRepository.getRooms()){
                is Result.Success -> _rooms.value = results.data
                is Result.Error<*> -> {

                }
            }
        }
    }

}