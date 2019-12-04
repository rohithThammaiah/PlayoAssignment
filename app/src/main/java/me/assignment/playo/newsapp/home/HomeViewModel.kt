package me.assignment.playo.newsapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import me.assignment.playo.newsapp.data.NewsRepo
import me.assignment.playo.newsapp.dateModels.Hits
import me.assignment.playo.newsapp.network.NetworkCallState
import timber.log.Timber

class HomeViewModel(
    private val newsRepo: NewsRepo
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val searchNCS = MutableLiveData<NetworkCallState<List<Hits>>>()

    fun getUIState(): LiveData<NetworkCallState<List<Hits>>> {
        return searchNCS
    }

    fun searchTerm(term: String) {
        if (term.isNotEmpty()) {
            searchNCS.value = NetworkCallState.Loading
            val networkCallDisposable = newsRepo.searchQuery(term)
                .subscribe({
                    searchNCS.value = NetworkCallState.Success(it.hits)
                }, {
                    Timber.e(it)
                    searchNCS.value =
                        NetworkCallState.Error(2, it.message ?: "Something went wrong")
                })

            compositeDisposable.add(networkCallDisposable)
        } else {
            searchNCS.value = NetworkCallState.Error(1, "Search Query cannot be blank")
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}