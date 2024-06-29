package com.dm.berxley.chatapp.data

data class ChatMessage (
    val message: String = "",
    val senderFirstName: String = "",
    val senderId: String = "",
    val timestamp: Long = 0L,
    val isSentByCurrentUser: Boolean = true
)