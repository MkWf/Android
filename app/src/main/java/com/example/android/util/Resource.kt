package com.example.android.util

//a wrapper for our response
sealed class Resource<T>(  //sealed class allows to restrict who can inherit from the resource class
    val data: T? = null,
    val message: String? = null
){
    class Success<T>(data:T) : Resource<T>(data) //data not null since if success then there will be a body
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>() //while we wait for Success or Error
}
