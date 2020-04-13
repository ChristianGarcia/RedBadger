package uk.co.origamibits.redbadger.reader

import uk.co.origamibits.redbadger.model.Orientation
import uk.co.origamibits.redbadger.model.StartingPoint
import uk.co.origamibits.redbadger.model.WorldGrid
import java.io.InputStream

class DefaultInputReader(
    private val worldGridParser: WorldGridParser,
    private val startingPointParser: StartingPointParser
) : InputReader {

    override fun read(inputStream: InputStream, block: (WorldGrid, StartingPoint, String) -> Unit) {
        val reader = inputStream.reader().buffered()

        val worldLine = reader.readLine() ?: throw IllegalArgumentException("Expected grid world with format <x> <y>'")
        val grid = worldGridParser.parse(worldLine)

        reader.useLines { lines ->
            lines
                .chunked(2)
                .filter { robotEntry -> robotEntry.size == 2 }
                .map { startingPointParser.parse(it[0]) to it[1] }
                .filter { (startingPoint, _) -> startingPoint != null }
                .forEach { (start, operations) -> block(grid, start!!, operations) }
        }

    }

}
