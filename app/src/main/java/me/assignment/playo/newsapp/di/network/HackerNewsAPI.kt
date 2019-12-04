package me.assignment.playo.newsapp.di.network

import retrofit2.http.GET
import retrofit2.http.Query

interface HackerNewsAPI {

    @GET("search")
    fun searchQuery(@Query("query") query: String)
}