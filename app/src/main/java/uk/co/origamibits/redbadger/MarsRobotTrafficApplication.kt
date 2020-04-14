package uk.co.origamibits.redbadger

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import uk.co.origamibits.redbadger.di.ApplicationModule
import uk.co.origamibits.redbadger.di.DaggerApplicationComponent
import javax.inject.Inject

class MarsRobotTrafficApplication : Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @ExperimentalStdlibApi
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        DaggerApplicationComponent.builder()
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}