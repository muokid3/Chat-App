package com.dm.berxley.chatapp.screens

sealed class Screen(val route: String) {
    object LoginScreen:Screen("login_screen")
    object SignUpScreen:Screen("sign_up_screen")
    object ChatRoomsScreen:Screen("chat_rooms_screen")
    object ChatScreen:Screen("chat_screen")
}