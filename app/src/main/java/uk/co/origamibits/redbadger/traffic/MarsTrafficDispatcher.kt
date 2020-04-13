package uk.co.origamibits.redbadger.traffic

import uk.co.origamibits.redbadger.reader.EarthStationReader
import uk.co.origamibits.redbadger.robot.RobotHiveMind
import java.io.InputStream

class MarsTrafficDispatcher(
    private val reader: EarthStationReader,
    private val robotHiveMind: RobotHiveMind
) {
    @ExperimentalStdlibApi
    fun dispatch(inputStream: InputStream) {
        reader.read(inputStream) { grid, start, instructions ->
            robotHiveMind.moveRobot(grid, start, instructions)
        }
    }


}