package uk.co.origamibits.redbadger.traffic

import uk.co.origamibits.redbadger.model.Cell
import uk.co.origamibits.redbadger.reader.EarthStationReader
import uk.co.origamibits.redbadger.robot.RobotHiveMind
import java.io.InputStream
import java.io.OutputStream

class MarsTrafficDispatcher(
    private val reader: EarthStationReader,
    private val robotHiveMind: RobotHiveMind
) {

    @ExperimentalStdlibApi
    fun dispatch(inputStream: InputStream, outputStream: OutputStream) {
        val bufferedWriter = outputStream.bufferedWriter()
        reader.read(inputStream) { grid, start, instructions ->
            val gridCopy = grid.copy()
            when (val result = robotHiveMind.moveRobot(grid, start, instructions)) {
                is RobotHiveMind.RobotMoveResult.Lost -> {
                    val (x, y, orientation) = result.scentLocation
                    gridCopy.grid[x][y] = Cell.ScentOfLostRobot
                    bufferedWriter.write("$x $y $orientation LOST")
                    bufferedWriter.newLine()
                }
                is RobotHiveMind.RobotMoveResult.Moved -> {
                    val (x, y, orientation) = result.location
                    bufferedWriter.write("$x $y $orientation")
                    bufferedWriter.newLine()

                }
            }
            gridCopy
        }
        bufferedWriter.close()
    }


}