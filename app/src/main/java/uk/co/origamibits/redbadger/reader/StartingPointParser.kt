package uk.co.origamibits.redbadger.reader

import timber.log.Timber
import uk.co.origamibits.redbadger.model.Orientation
import uk.co.origamibits.redbadger.model.RobotLocation

class StartingPointParser {
    fun parse(line: String): RobotLocation? {
        val split = line.split(" ")
        if (split.size != 3) {
            Timber.w("Invalid starting point. Missing values: %s", line)
            return null
        }
        val (x, y) = try {
            split[0].toInt() to split[1].toInt()
        } catch (e: NumberFormatException) {
            Timber.w("Invalid starting point. Expected only numeric coordinates: %s", line)
            return null
        }
        if (x < 0 || y < 0) {
            Timber.w("Invalid starting point. Expected only positive coordinates: %s", line)
            return null
        }
        return when (split[2]) {
            "N" -> RobotLocation(x, y, Orientation.N)
            "S" -> RobotLocation(x, y, Orientation.S)
            "W" -> RobotLocation(x, y, Orientation.W)
            "E" -> RobotLocation(x, y, Orientation.E)
            else -> null
        }
    }

}
