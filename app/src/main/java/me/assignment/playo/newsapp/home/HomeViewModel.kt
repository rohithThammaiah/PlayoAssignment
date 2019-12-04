package me.assignment.playo.newsapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import me.assignment.playo.newsapp.data.NewsRepo
import me.assignment.playo.newsapp.network.NetworkCallState
import timber.log.Timber

class HomeViewModel(
    private val newsRepo: NewsRepo
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val searchNCS = MutableLiveData<NetworkCallState<Unit>>()

    fun searchTerm(term: String): LiveData<NetworkCallState<Unit>> {
        if (term.isNotEmpty()) {
            searchNCS.value = NetworkCallState.Loading
            val networkCallDisposable = newsRepo.searchQuery(term)
                .subscribe({
                    searchNCS.value = NetworkCallState.Success(Unit)
                }, {
                    Timber.e(it)
                    searchNCS.value =
                        NetworkCallState.Error(2, it.message ?: "Something went wrong")
                })

            compositeDisposable.add(networkCallDisposable)
        } else {
            searchNCS.value = NetworkCallState.Error(1, "Search Query cannot be blank")
        }

        return searchNCS
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}