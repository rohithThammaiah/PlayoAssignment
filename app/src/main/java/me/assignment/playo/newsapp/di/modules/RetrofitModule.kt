package me.assignment.playo.newsapp.di.modules

import dagger.Module
import dagger.Provides
import me.assignment.playo.newsapp.di.scopes.ApplicationScope
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named

@Module(includes = [NetworkModule::class, RetrofitConverter::class])
class RetrofitModule {

    @Provides
    @ApplicationScope
    fun providesRetrofitInstance(
        httpClient: OkHttpClient,
        @Named("baseUrl") baseUrl: String,
        moshiConverterFactory: MoshiConverterFactory,
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .client(httpClient)
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideRxJavaCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }
}