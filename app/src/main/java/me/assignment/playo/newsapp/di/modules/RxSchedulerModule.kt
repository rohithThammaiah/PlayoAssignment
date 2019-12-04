package me.assignment.playo.newsapp.di.modules

import dagger.Module
import dagger.Provides
import me.assignment.playo.newsapp.di.scopes.ApplicationScope
import me.assignment.playo.newsapp.rx.AppSchedulerProvider
import me.assignment.playo.newsapp.rx.SchedulerProvider

@Module
class RxSchedulerModule {

    @Provides
    @ApplicationScope
    fun provideScheduler(): SchedulerProvider {
        return AppSchedulerProvider()
    }

}