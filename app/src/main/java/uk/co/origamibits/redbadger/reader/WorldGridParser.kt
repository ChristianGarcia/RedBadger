package uk.co.origamibits.redbadger.reader

import uk.co.origamibits.redbadger.model.WorldGrid
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorldGridParser @Inject constructor() {
    fun parse(worldLine: String): WorldGrid {
        val (x, y) = try {
            worldLine.split(" ").let { it[0].toInt() to it[1].toInt() }
        } catch (e: IndexOutOfBoundsException) {
            throw IllegalArgumentException("Expected world grid with format <x> <y>': $worldLine")
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Expected world grid with format <x> <y>': $worldLine")
        }
        if (x < 0 || y < 0) throw IllegalArgumentException("Expected world grid with only positive numbers: $worldLine")
        return WorldGrid(x, y)
    }

}
