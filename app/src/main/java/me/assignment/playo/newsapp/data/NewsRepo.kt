package me.assignment.playo.newsapp.data

import io.reactivex.Single
import me.assignment.playo.newsapp.di.scopes.ApplicationScope
import me.assignment.playo.newsapp.network.HackerNewsAPI
import me.assignment.playo.newsapp.rx.SchedulerProvider
import javax.inject.Inject

@ApplicationScope
class NewsRepo @Inject constructor(
    private val hackerNewsAPI: HackerNewsAPI,
    private val schedulerProvider: SchedulerProvider
) {

    fun searchQuery(query: String): Single<Unit> {
        return hackerNewsAPI.searchQuery(query)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

}