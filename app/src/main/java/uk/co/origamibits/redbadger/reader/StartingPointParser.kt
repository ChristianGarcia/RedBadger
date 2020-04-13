package uk.co.origamibits.redbadger.reader

import timber.log.Timber
import uk.co.origamibits.redbadger.model.Orientation
import uk.co.origamibits.redbadger.model.StartingPoint

class StartingPointParser {
    fun parse(line: String): StartingPoint? {
        val split = line.split(" ")
        if (split.size != 3) {
            return null
        }
        val (x, y) = try {
            split[0].toInt() to split[1].toInt()
        } catch (e: NumberFormatException) {
            return null
        }
        if (x < 0 || y < 0) return null
        return when (split[2]) {
            "N" -> StartingPoint(x, y, Orientation.N)
            "S" -> StartingPoint(x, y, Orientation.S)
            "W" -> StartingPoint(x, y, Orientation.W)
            "E" -> StartingPoint(x, y, Orientation.E)
            else -> null
        }
    }

}
