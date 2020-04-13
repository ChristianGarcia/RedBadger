package uk.co.origamibits.redbadger

import timber.log.Timber
import uk.co.origamibits.redbadger.reader.EarthStationReader
import uk.co.origamibits.redbadger.reader.StartingPointParser
import uk.co.origamibits.redbadger.reader.WorldGridParser
import uk.co.origamibits.redbadger.robot.RobotHiveMind
import uk.co.origamibits.redbadger.traffic.MarsTrafficDispatcher
import java.io.File

@ExperimentalStdlibApi
object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        Timber.plant(StdOutTree())
        val dispatcher = MarsTrafficDispatcher(EarthStationReader(WorldGridParser(), StartingPointParser()), RobotHiveMind())
        val input = File(File("app/src/main/res/raw"), "robots_input.txt")
        val output = File(File("app/build"), "robots_output.txt").apply {
            createNewFile()
        }
        dispatcher.dispatch(input.inputStream(), output.outputStream())
    }

    class StdOutTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            println(message)
        }

    }

}