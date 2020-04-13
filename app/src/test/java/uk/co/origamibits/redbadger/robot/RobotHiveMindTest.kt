package uk.co.origamibits.redbadger.robot

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import uk.co.origamibits.redbadger.model.Orientation
import uk.co.origamibits.redbadger.model.RobotLocation
import uk.co.origamibits.redbadger.model.WorldGrid

class RobotHiveMindTest {

    private lateinit var hiveMind: RobotHiveMind

    @Before
    fun setUp() {
        hiveMind = RobotHiveMind()
    }

    @Test
    fun `given start out of grid, when move, then return null`() {
        val lastPosition = hiveMind.moveRobot(WorldGrid(0, 0), RobotLocation(1, 1, Orientation.N), charArrayOf())
        assertThat(lastPosition).isNull()
    }

    @Test
    fun `given instructions move robot out of grid, when move, then return lost`() {
        val lastPosition = hiveMind.moveRobot(
            grid = WorldGrid(1, 1),
            start = RobotLocation(1, 1, Orientation.N),
            instructions = "F".toCharArray()
        )
        assertThat(lastPosition).isEqualTo(RobotHiveMind.RobotMoveResult.Lost(1 to 1))
    }

    @Test
    fun `given instructions move robot within grid, when move, then return within`() {
        val lastPosition = hiveMind.moveRobot(
            grid = WorldGrid(2, 2),
            start = RobotLocation(1, 1, Orientation.N),
            instructions = "F".toCharArray()
        )
        assertThat(lastPosition).isEqualTo(RobotHiveMind.RobotMoveResult.Moved(1 to 2))
    }
}