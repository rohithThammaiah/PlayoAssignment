package me.assignment.playo.newsapp

import android.app.Application
import com.facebook.stetho.Stetho
import me.assignment.playo.newsapp.di.DaggerPlayoNewsAppComponent
import me.assignment.playo.newsapp.di.PlayoNewsAppComponent
import me.assignment.playo.newsapp.di.modules.ContextModule
import timber.log.Timber

class PlayoNewsApplication : Application() {

    lateinit var appComponent : PlayoNewsAppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerPlayoNewsAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }
    }
}