package me.assignment.playo.newsapp.home

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.home_screen.*
import me.assignment.playo.newsapp.PlayoNewsApplication
import me.assignment.playo.newsapp.R
import me.assignment.playo.newsapp.ViewModelFactory
import me.assignment.playo.newsapp.dateModels.Hits
import me.assignment.playo.newsapp.network.NetworkCallState
import me.assignment.playo.newsapp.newsDetails.NewsDetailsScreen
import me.assignment.playo.newsapp.recyclerview.OnRVItemClickListener
import me.assignment.playo.newsapp.rx.SchedulerProvider
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeScreen : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var searchListAdapter: SearchListAdapter

    private val onPauseDisposables = CompositeDisposable()
    private val onDestroyDisposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)

        (application as PlayoNewsApplication).appComponent.inject(this)
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)

        val onSearchItemClickListener = object : OnRVItemClickListener<Hits> {
            override fun onClick(item: Hits, view: View) {
                val intent = NewsDetailsScreen.getIntent(this@HomeScreen,item.url)
                startActivity(intent)
            }
        }

        searchListAdapter = SearchListAdapter(onSearchItemClickListener)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        indeterminate_progress_bar.hide()
        initial_no_data_text.visibility = View.VISIBLE
        home_search_list.adapter = searchListAdapter

        homeViewModel.getUIState().observe(this, Observer {
            when (it) {
                is NetworkCallState.Loading -> {
                    indeterminate_progress_bar.show()
                }

                is NetworkCallState.Error -> {
                    indeterminate_progress_bar.hide()
                    val snackbar = Snackbar.make(window.decorView, it.errorMessage,Snackbar.LENGTH_INDEFINITE)
                        snackbar.setAction("Dismiss") {
                            snackbar.dismiss()
                        }
                    snackbar.show()
                }

                is NetworkCallState.Success -> {
                    indeterminate_progress_bar.hide()
                    initial_no_data_text.visibility = View.GONE
                    searchListAdapter.submitList(it.data)
                }
            }
        })

        home_search_view.setOnCloseListener {
            searchListAdapter.submitList(listOf())
            initial_no_data_text.visibility = View.VISIBLE
            return@setOnCloseListener true
        }

        home_search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { homeViewModel.searchTerm(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (TextUtils.isEmpty(newText)) {
                    searchListAdapter.submitList(listOf())
                }

                val disposable = Observable.just(newText)
                    .filter { it.length > 2 }
                    .map { it }
                    .debounce(400, TimeUnit.MILLISECONDS)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({
                        homeViewModel.searchTerm(it)
                    }, {
                        Timber.e(it)
                    })

                onPauseDisposables.add(disposable)
                return true
            }
        })
    }

    override fun onPause() {
        onPauseDisposables.clear()
        super.onPause()
    }

    override fun onDestroy() {
        onDestroyDisposables.clear()
        super.onDestroy()
    }
}
