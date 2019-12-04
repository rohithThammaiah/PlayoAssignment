package me.assignment.playo.newsapp.di.network

sealed class NetworkCallState {
    object Loading : NetworkCallState()
    data class Success(val data: Any?) : NetworkCallState()
    data class Error(val message: String) : NetworkCallState()
}