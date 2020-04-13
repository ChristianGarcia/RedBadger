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
}