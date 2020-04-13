package uk.co.origamibits.redbadger.traffic

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Before
import org.junit.Test
import timber.log.Timber
import uk.co.origamibits.redbadger.UnitTestTree
import uk.co.origamibits.redbadger.reader.EarthStationReader
import uk.co.origamibits.redbadger.reader.StartingPointParser
import uk.co.origamibits.redbadger.reader.WorldGridParser
import uk.co.origamibits.redbadger.robot.RobotHiveMind

class MarsTrafficDispatcherTest {

    private lateinit var dispatcher: MarsTrafficDispatcher

    private val reader: EarthStationReader = EarthStationReader(WorldGridParser(), StartingPointParser())
    private val hiveMind: RobotHiveMind = mock()

    @Before
    fun setUp() {
        Timber.plant(UnitTestTree())
        dispatcher = MarsTrafficDispatcher(reader, hiveMind)
    }

    @Test
    fun `given reader fails, when dispatch, then fail`() {
        given(reader.read(any(), any())).willThrow(IllegalArgumentException())

        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy {
            dispatcher.dispatch("".byteInputStream())
        }
    }

    @Test
    fun `given reader succeeds, when dispatch, then move all robots`() {

        dispatcher.dispatch(
            """
            5 3
            1 1 E
            RFRFRFRF
            
            3 2 N
            FRRFLLFFRRFLL
            
            0 3 W
            LLFFFLFLFL
        """.trimIndent().byteInputStream()
        )

        verify(hiveMind, times(3)).moveRobot(any(), any(), any())
    }
}