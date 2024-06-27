package com.dm.berxley.chatapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.berxley.chatapp.data.Result
import com.dm.berxley.chatapp.data.UserRepository
import com.dm.berxley.chatapp.utils.Injection
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    private val userRepository: UserRepository
    init {
        userRepository = UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance())
    }

    private val _authResult = MutableLiveData<Result<Boolean>>()
    val authResult: MutableLiveData<Result<Boolean>> get() = _authResult


    fun signUp(email: String, password:String, firstName:String, lastName: String){
        viewModelScope.launch {
            _authResult.value = userRepository.signUp(email = email, password = password, firstName = firstName, lastName = lastName)
        }
    }


}