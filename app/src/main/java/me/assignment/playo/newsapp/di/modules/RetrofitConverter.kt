package me.assignment.playo.newsapp.di.modules

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import me.assignment.playo.newsapp.di.scopes.ApplicationScope
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
class RetrofitConverter {

    @Provides
    @ApplicationScope
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @ApplicationScope
    fun provideMoshiRetrofitConverter(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }
}