package uk.co.origamibits.redbadger.reader

import uk.co.origamibits.redbadger.model.Orientation
import uk.co.origamibits.redbadger.model.StartingPoint
import uk.co.origamibits.redbadger.model.WorldGrid
import java.io.InputStream

class DefaultInputReader : InputReader {

    override fun read(inputStream: InputStream, block: (WorldGrid, StartingPoint, String) -> Unit) {
        val lines = inputStream.reader().readLines()

        val worldLine = lines.firstOrNull() ?: throw IllegalArgumentException("Expected grid world with format <x> <y>'")
        val grid = parseWorldGrid(worldLine)

        lines.subList(1, lines.size)
            .chunked(2)
            .filter { robotEntry -> robotEntry.size == 2 }
            .map { parseStartingPoint(it[0]) to it[1] }
            .filter { (startingPoint, _) -> startingPoint != null }
            .forEach { (start, operations) -> block(grid, start!!, operations) }

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

    private fun parseWorldGrid(worldLine: String): WorldGrid {
        val (x, y) = try {
            worldLine.split(" ").let { it[0].toInt() to it[1].toInt() }
        } catch (e: IndexOutOfBoundsException) {
            throw IllegalArgumentException("Expected grid world with format <x> <y>'")
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException(e)
        }
        if (x < 0 || y < 0) throw IllegalArgumentException("Expected world grid with only positive numbers: $worldLine")
        return WorldGrid(x, y)
    }
}
