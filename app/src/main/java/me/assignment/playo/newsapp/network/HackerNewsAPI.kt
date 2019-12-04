package me.assignment.playo.newsapp.network

import io.reactivex.Observable
import me.assignment.playo.newsapp.dateModels.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HackerNewsAPI {

    @GET("search")
    fun searchQuery(
        @Query("query") query: String,
        @Query("hitsPerPage") hitsPerPage: Int = 5
    ): Observable<SearchResponse>
}