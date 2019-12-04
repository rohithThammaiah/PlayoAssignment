package me.assignment.playo.newsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.assignment.playo.newsapp.data.NewsRepo
import me.assignment.playo.newsapp.di.scopes.ApplicationScope
import me.assignment.playo.newsapp.home.HomeViewModel
import javax.inject.Inject

@ApplicationScope
class ViewModelFactory @Inject constructor(
    private val newsRepo: NewsRepo
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(HomeViewModel::class.java) -> return@with HomeViewModel(
                    newsRepo
                )

                else -> throw IllegalThreadStateException("Unknown class: $modelClass")
            }
        } as T
}