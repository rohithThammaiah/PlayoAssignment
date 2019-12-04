package me.assignment.playo.newsapp

import dagger.Component
import di.MockWebServerModule
import di.TestBaseUrlModule
import di.TestSchedulerModule
import me.assignment.playo.newsapp.di.PlayoNewsAppComponent
import me.assignment.playo.newsapp.di.modules.ApiModule
import me.assignment.playo.newsapp.di.modules.ContextModule
import me.assignment.playo.newsapp.di.scopes.ApplicationScope
import me.assignment.playo.newsapp.home.HomeViewModelTest
import okhttp3.mockwebserver.MockWebServer

@ApplicationScope
@Component(modules = [
    ContextModule::class,
    ApiModule::class,
    TestSchedulerModule::class,
    TestBaseUrlModule::class,
    MockWebServerModule::class
])
interface TestPlayoAppComponent: PlayoNewsAppComponent {
    fun getMockWebServer(): MockWebServer
    fun inject(homeViewModelTest: HomeViewModelTest)
}