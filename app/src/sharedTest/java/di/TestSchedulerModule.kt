package di

import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.TestScheduler
import me.assignment.playo.newsapp.di.scopes.ApplicationScope
import me.assignment.playo.newsapp.rx.SchedulerProvider
import testUtils.TestSchedulerProvider


@Module
class TestSchedulerModule {

    @Provides
    @ApplicationScope
    fun provideScheduler(testScheduler: TestScheduler): SchedulerProvider {
        return TestSchedulerProvider(testScheduler)
    }

    @Provides
    @ApplicationScope
    fun provideTestScheduler(): TestScheduler {
        return TestScheduler()
    }
}