package com.example.openlib.data.remote

// T refere á Weathermodel
sealed class NetworkResponse <out T> {

    data class Success<out T>(val data:T):NetworkResponse<T>()
    data class Error(val message : String): NetworkResponse<Nothing>()
    object Loading :NetworkResponse<Nothing>()

}