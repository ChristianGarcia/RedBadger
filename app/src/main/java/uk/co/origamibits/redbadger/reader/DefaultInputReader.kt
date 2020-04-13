package uk.co.origamibits.redbadger.reader

import uk.co.origamibits.redbadger.model.WorldGrid
import java.io.InputStream

class DefaultInputReader : InputReader {

    override fun read(inputStream: InputStream, operation: (String) -> Unit): WorldGrid {
        val worldLine = inputStream.reader().readLines().firstOrNull() ?: throw IllegalArgumentException()
        val (x, y) = try {
            worldLine.split(" ").let { it[0].toInt() to it[1].toInt() }
        } catch (e: IndexOutOfBoundsException) {
            throw IllegalArgumentException("Expected grid world with format <x> <y>'")
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException(e)
        }
        if (x < 0 || y < 0) throw IllegalArgumentException("Expected world grid with only positive numbers: $worldLine ")
        return WorldGrid(x, y)
    }

}
