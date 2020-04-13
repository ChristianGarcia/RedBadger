package uk.co.origamibits.redbadger

import android.util.Log
import timber.log.Timber

class UnitTestTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val safeTag = tag?.let { "$it: " } ?: ""
        val line = "$safeTag$message"
        when (priority) {
            Log.ASSERT, Log.ERROR -> System.err.println(line)
            else -> println(line)
        }
        t?.printStackTrace(System.err)
    }

}
