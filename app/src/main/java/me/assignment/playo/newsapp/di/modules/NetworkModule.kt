package me.assignment.playo.newsapp.di.modules

import android.content.Context
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import me.assignment.playo.newsapp.BuildConfig
import me.assignment.playo.newsapp.di.scopes.ApplicationScope
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File

@Module(includes = [ContextModule::class])
class NetworkModule {


    @Provides
    @ApplicationScope
    fun providesOkHttpClient(
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor,
        stethoInterceptor: StethoInterceptor
    ): OkHttpClient {
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addNetworkInterceptor(stethoInterceptor)
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build()
        } else {
            OkHttpClient.Builder()
                .cache(cache)
                .build()
        }
    }


    @Provides
    @ApplicationScope
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.i(message)
            }
        })
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

    }

    @Provides
    @ApplicationScope
    fun providesCache(cacheFile: File): Cache {
        return Cache(cacheFile, 10 * 1000 * 1000)
    }


    @Provides
    @ApplicationScope
    fun providesCacheFile(context: Context): File {
        return File(context.cacheDir, "okhttp_cache")
    }

    @Provides
    @ApplicationScope
    fun providesStethoDebugger(): StethoInterceptor {
        return StethoInterceptor()
    }

}