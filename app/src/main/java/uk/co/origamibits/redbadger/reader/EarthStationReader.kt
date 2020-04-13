package uk.co.origamibits.redbadger.reader

import uk.co.origamibits.redbadger.model.RobotLocation
import uk.co.origamibits.redbadger.model.WorldGrid
import java.io.InputStream

class EarthStationReader(
    private val worldGridParser: WorldGridParser,
    private val startingPointParser: StartingPointParser
) {

    @ExperimentalStdlibApi
    fun read(inputStream: InputStream, block: (WorldGrid, RobotLocation, CharArray) -> WorldGrid) {
        val reader = inputStream.reader().buffered()

        val worldLine = reader.readLine() ?: throw IllegalArgumentException("Expected grid world with format <x> <y>'")
        val grid = worldGridParser.parse(worldLine).let { (x, y) -> WorldGrid(x, y) }

        reader.useLines { lines ->
            lines
                .filter { it.isNotBlank() }
                .chunked(2)
                .filter { robotEntry -> robotEntry.size == 2 }
                .map { startingPointParser.parse(it[0]) to it[1] }
                .filter { (startingPoint, _) -> startingPoint != null }
                .scan(grid) { currentGrid, robotEntry ->
                    val (start, instructions) = robotEntry
                    block(currentGrid, start!!, instructions.toCharArray())
                }
                .forEach { _ -> } //Subscribes to this sequence to start consuming it

        }

    }

}
