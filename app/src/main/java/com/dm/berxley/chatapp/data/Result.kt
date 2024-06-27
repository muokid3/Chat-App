package com.dm.berxley.chatapp.data

import java.lang.Exception

sealed class Result<out T> {
    data class Success<out T> (val data: T): Result<T>()
    data class Error<out T> (val exception: T): Result<Nothing>()
}