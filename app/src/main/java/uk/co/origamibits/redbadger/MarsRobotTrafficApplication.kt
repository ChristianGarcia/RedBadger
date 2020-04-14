package uk.co.origamibits.redbadger

import android.app.Application
import timber.log.Timber

class MarsRobotTrafficApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}