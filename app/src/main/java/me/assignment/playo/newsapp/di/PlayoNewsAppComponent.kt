package me.assignment.playo.newsapp.di

import dagger.Component
import me.assignment.playo.newsapp.di.modules.ApiModule
import me.assignment.playo.newsapp.di.modules.BaseUrlModule
import me.assignment.playo.newsapp.di.modules.ContextModule
import me.assignment.playo.newsapp.di.modules.RxSchedulerModule
import me.assignment.playo.newsapp.di.scopes.ApplicationScope

@ApplicationScope
@Component(modules = [
    ContextModule::class,
    ApiModule::class,
    RxSchedulerModule::class,
    BaseUrlModule::class
])
interface PlayoNewsAppComponent {
}