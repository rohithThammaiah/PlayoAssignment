package me.assignment.playo.newsapp.di.modules

import dagger.Module
import dagger.Provides
import me.assignment.playo.newsapp.di.scopes.ApplicationScope
import me.assignment.playo.newsapp.network.HackerNewsAPI
import retrofit2.Retrofit

@Module(includes = [RetrofitModule::class])
class ApiModule {

    @Provides
    @ApplicationScope
    fun provideWikiApi(retrofit: Retrofit): HackerNewsAPI {
        return retrofit.create(HackerNewsAPI::class.java)
    }
}