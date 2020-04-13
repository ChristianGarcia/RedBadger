package uk.co.origamibits.redbadger.robot

import uk.co.origamibits.redbadger.model.Orientation
import uk.co.origamibits.redbadger.model.RobotLocation
import uk.co.origamibits.redbadger.model.WorldGrid

class RobotHiveMind {

    fun moveRobot(grid: WorldGrid, start: RobotLocation, instructions: CharArray): RobotMoveResult? {
        if (!grid.contains(start.coords)) {
            return null
        }
        var lastKnownPosition: RobotLocation = start

        instructions.forEach { instruction ->
            val newPosition = when (instruction) {
                'F' -> when (lastKnownPosition.orientation) {
                    Orientation.N -> lastKnownPosition.copy(y = lastKnownPosition.y + 1)
                    Orientation.S -> lastKnownPosition.copy(y = lastKnownPosition.y - 1)
                    Orientation.E -> lastKnownPosition.copy(x = lastKnownPosition.x + 1)
                    Orientation.W -> lastKnownPosition.copy(x = lastKnownPosition.x - 1)
                }
                'R' -> when (lastKnownPosition.orientation) {
                    Orientation.N -> lastKnownPosition.copy(orientation = Orientation.E)
                    Orientation.S -> lastKnownPosition.copy(orientation = Orientation.W)
                    Orientation.E -> lastKnownPosition.copy(orientation = Orientation.S)
                    Orientation.W -> lastKnownPosition.copy(orientation = Orientation.N)
                }
                'L' -> when (lastKnownPosition.orientation) {
                    Orientation.N -> lastKnownPosition.copy(orientation = Orientation.W)
                    Orientation.S -> lastKnownPosition.copy(orientation = Orientation.E)
                    Orientation.E -> lastKnownPosition.copy(orientation = Orientation.N)
                    Orientation.W -> lastKnownPosition.copy(orientation = Orientation.S)
                }
                else -> lastKnownPosition
            }
            if (!grid.contains(newPosition.coords)) {
                return RobotMoveResult.Lost(lastKnownPosition.coords)
            } else {
                lastKnownPosition = newPosition
            }
        }
        return RobotMoveResult.Moved(lastKnownPosition.coords)
    }

    sealed class RobotMoveResult {
        data class Lost(val scentLocation: Pair<Int, Int>) : RobotMoveResult()
        data class Moved(val location: Pair<Int, Int>) : RobotMoveResult()
    }

    private val RobotLocation.coords get() = x to y

}
