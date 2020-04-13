package uk.co.origamibits.redbadger.reader

import uk.co.origamibits.redbadger.model.Orientation
import uk.co.origamibits.redbadger.model.StartingPoint
import uk.co.origamibits.redbadger.model.WorldGrid
import java.io.InputStream

class DefaultInputReader(
    private val worldGridParser: WorldGridParser
) : InputReader {

    override fun read(inputStream: InputStream, block: (WorldGrid, StartingPoint, String) -> Unit) {
        val reader = inputStream.reader().buffered()

        val worldLine = reader.readLine() ?: throw IllegalArgumentException("Expected grid world with format <x> <y>'")
        val grid = worldGridParser.parse(worldLine)

        reader.useLines { lines ->
            lines
                .chunked(2)
                .filter { robotEntry -> robotEntry.size == 2 }
                .map { parseStartingPoint(it[0]) to it[1] }
                .filter { (startingPoint, _) -> startingPoint != null }
                .forEach { (start, operations) -> block(grid, start!!, operations) }
        }

    }

    private fun parseStartingPoint(line: String): StartingPoint? {
        val split = line.split(" ")
        if (split.size != 3) return null
        val (x, y) = try {
            split[0].toInt() to split[1].toInt()
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException(e)
        }
        return when (split[2]) {
            "N" -> StartingPoint(x, y, Orientation.N)
            "S" -> StartingPoint(x, y, Orientation.S)
            "W" -> StartingPoint(x, y, Orientation.W)
            "E" -> StartingPoint(x, y, Orientation.E)
            else -> null
        }
    }

}
