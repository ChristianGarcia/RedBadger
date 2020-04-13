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
        val folder = File("app/src/main/res/raw")
        println(folder.absolutePath)
        val input = File(folder, "robots_input.txt")
        val output = File(folder, "robots_output.txt").apply {
            if (!exists()) {
                createNewFile()
            }
        }
        dispatcher.dispatch(input.inputStream(), output.outputStream().buffered())
    }

    class StdOutTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            println(message)
        }

    }

}