package me.assignment.playo.newsapp.network

sealed class NetworkCallState<out T: Any?> {
    object Loading: NetworkCallState<Nothing>()
    class Success<out T:Any>(val data: T?): NetworkCallState<T>()
    class Error(val errorType: Int,val errorMessage: String): NetworkCallState<Nothing>()
}