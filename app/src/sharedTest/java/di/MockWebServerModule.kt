package di

import dagger.Module
import dagger.Provides
import me.assignment.playo.newsapp.di.scopes.ApplicationScope
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.buffer
import okio.source
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

@Module
class MockWebServerModule {

    @Provides
    @ApplicationScope
    fun provideMockServer(dispatcher: Dispatcher): MockWebServer {
        var mockWebServer: MockWebServer? = null
        val thread = Thread(Runnable {
            mockWebServer = MockWebServer()
            mockWebServer?.dispatcher = dispatcher
            mockWebServer?.start()
        })
        thread.start()
        thread.join()
        return mockWebServer ?: throw NullPointerException()
    }

    @Provides
    @ApplicationScope
    fun provideDispatcher(): Dispatcher {
        return object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path?.contains("search")) {
                     true -> {
                        val inputStream = javaClass.classLoader?.getResourceAsStream("responses/search_response.json")
                        val source = inputStream!!.source().buffer()
                        source.readString(StandardCharsets.UTF_8).let {
                            MockResponse()
                                .setBodyDelay(400,TimeUnit.MILLISECONDS)
                                .setResponseCode(200)
                                .setBody(
                                    it
                                )
                        }
                    }

                    else -> MockResponse().setResponseCode(404)
                }
            }
        }
    }
}