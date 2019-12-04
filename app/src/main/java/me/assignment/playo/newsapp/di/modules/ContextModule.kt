package me.assignment.playo.newsapp.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import me.assignment.playo.newsapp.di.scopes.ApplicationScope

@Module
class ContextModule(val context: Context) {

    @Provides
    @ApplicationScope
    fun providesContext(): Context {
        return context
    }
}