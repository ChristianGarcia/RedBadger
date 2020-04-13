package uk.co.origamibits.redbadger.reader

import uk.co.origamibits.redbadger.model.WorldGrid

class WorldGridParser {
    fun parse(worldLine: String): WorldGrid {
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
