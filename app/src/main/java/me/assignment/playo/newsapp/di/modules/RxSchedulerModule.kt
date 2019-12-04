package me.assignment.playo.newsapp.di.modules

import dagger.Module
import dagger.Provides
import me.assignment.playo.newsapp.di.rx.AppSchedulerProvider
import me.assignment.playo.newsapp.di.rx.SchedulerProvider
import me.assignment.playo.newsapp.di.scopes.ApplicationScope
import javax.inject.Named

@Module
class RxSchedulerModule {

    @Provides
    @ApplicationScope
    @Named("scheduler")
    fun provideScheduler(): SchedulerProvider {
        return AppSchedulerProvider()
    }

}