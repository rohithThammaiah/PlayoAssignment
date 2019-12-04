package testUtils

import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler
import me.assignment.playo.newsapp.rx.SchedulerProvider

class TestSchedulerProvider(private val mTestScheduler: TestScheduler) : SchedulerProvider {

    override fun computation(): Scheduler {
        return mTestScheduler
    }

    override fun io(): Scheduler {
        return mTestScheduler
    }

    override fun ui(): Scheduler {
        return mTestScheduler
    }
}
