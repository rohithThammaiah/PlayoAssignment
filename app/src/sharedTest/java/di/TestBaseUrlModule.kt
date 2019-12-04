package di

import dagger.Module
import dagger.Provides
import me.assignment.playo.newsapp.di.scopes.ApplicationScope
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Named

@Module
class TestBaseUrlModule {

    @Provides
    @ApplicationScope
    @Named("baseUrl")
    fun provideBaseUrl(mockWebServer: MockWebServer): String {
        var url = ""
        val t = Thread(Runnable {
            url = mockWebServer.url("/").toString()
        })
        t.start()
        t.join()
        return url
    }

}