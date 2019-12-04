package me.assignment.playo.newsapp.di.modules

import dagger.Module
import dagger.Provides
import me.assignment.playo.newsapp.di.scopes.ApplicationScope
import javax.inject.Named

@Module
class BaseUrlModule {

    @Provides
    @ApplicationScope
    @Named("baseUrl")
    fun provideBaseUrl(): String {
        return "http://hn.algolia.com/api/v1/"
    }
}