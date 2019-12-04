package me.assignment.playo.newsapp.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import io.reactivex.schedulers.TestScheduler
import junit.framework.TestCase
import me.assignment.playo.newsapp.DaggerTestPlayoAppComponent
import me.assignment.playo.newsapp.data.NewsRepo
import me.assignment.playo.newsapp.di.modules.ContextModule
import me.assignment.playo.newsapp.network.NetworkCallState
import me.assignment.wikiapp.testUtils.observeForTesting
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var newsRepo: NewsRepo
    @Inject
    lateinit var testScheduler: TestScheduler

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().context
        val appComponent = DaggerTestPlayoAppComponent.builder()
            .contextModule(ContextModule(context)).build()
        appComponent.inject(this)

        homeViewModel = HomeViewModel(newsRepo)
    }

    @Test
    fun `when term is blank then status should be Error with errorType as 1 and a valid error message`() {
        // Given
        val termToBeSearch = ""
        val networkCallState = homeViewModel.getUIState()
        // When
        homeViewModel.searchTerm(termToBeSearch)
        // Then
        networkCallState.observeForTesting {
            Truth.assertThat(networkCallState.value is NetworkCallState.Error).isTrue()
            val value = networkCallState.value as NetworkCallState.Error
            TestCase.assertEquals(1, value.errorType)
            TestCase.assertEquals("Search Query cannot be blank", value.errorMessage)
        }
    }

    @Test
    fun `when term is not blank then status should be Success`() {
        // Given
        val termToBeSearch = "sports"
        val networkCallState = homeViewModel.getUIState()
        // When
        homeViewModel.searchTerm(termToBeSearch)
        // Then
        networkCallState.observeForTesting {
            Truth.assertThat(networkCallState.value is NetworkCallState.Loading).isTrue()
            testScheduler.triggerActions()
            //then
            Truth.assertThat(networkCallState.value is NetworkCallState.Success).isTrue()
            val value = networkCallState.value as NetworkCallState.Success
        }
    }

}