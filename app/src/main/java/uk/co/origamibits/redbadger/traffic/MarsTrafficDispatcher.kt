package uk.co.origamibits.redbadger.traffic

import uk.co.origamibits.redbadger.model.Cell
import uk.co.origamibits.redbadger.reader.EarthStationReader
import uk.co.origamibits.redbadger.robot.RobotHiveMind
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarsTrafficDispatcher @Inject constructor(
    private val reader: EarthStationReader,
    private val robotHiveMind: RobotHiveMind
) {

    @ExperimentalStdlibApi
    fun dispatch(inputStream: InputStream, outputStream: OutputStream) {
        outputStream.bufferedWriter().use {

            reader.read(inputStream) { grid, start, instructions ->
                val gridCopy = grid.copy()
                when (val result = robotHiveMind.moveRobot(grid, start, instructions)) {
                    is RobotHiveMind.RobotMoveResult.Lost -> {
                        val (x, y, orientation) = result.scentLocation
                        gridCopy.grid[x][y] = Cell.ScentOfLostRobot
                        it.write("$x $y $orientation LOST")
                        it.newLine()
                    }
                    is RobotHiveMind.RobotMoveResult.Moved -> {
                        val (x, y, orientation) = result.location
                        it.write("$x $y $orientation")
                        it.newLine()
                    }
                }
                gridCopy
            }
        }
    }


}