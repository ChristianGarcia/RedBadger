package uk.co.origamibits.redbadger.traffic

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Before
import org.junit.Test
import timber.log.Timber
import uk.co.origamibits.redbadger.UnitTestTree
import uk.co.origamibits.redbadger.reader.EarthStationReader
import uk.co.origamibits.redbadger.reader.StartingPointParser
import uk.co.origamibits.redbadger.reader.WorldGridParser
import uk.co.origamibits.redbadger.robot.RobotHiveMind
import java.io.ByteArrayOutputStream

@ExperimentalStdlibApi
class MarsTrafficDispatcherTest {

    @Before
    fun setUp() {
        Timber.plant(UnitTestTree())
    }

    @Test
    fun `given reader fails, when dispatch, then fail`() {
        val reader: EarthStationReader = mock()
        given(reader.read(any(), any())).willThrow(IllegalArgumentException())
        val dispatcher = MarsTrafficDispatcher(reader, mock())

        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy {
            dispatcher.dispatch("".byteInputStream(), ByteArrayOutputStream())
        }
    }

    //Partial integration test
    @Test
    fun `given reader succeeds, when dispatch, then move all robots`() {
        val robotHiveMind: RobotHiveMind = mock()
        val dispatcher = MarsTrafficDispatcher(
            reader = EarthStationReader(WorldGridParser(), StartingPointParser()), //Integration
            robotHiveMind = robotHiveMind
        )
        dispatcher.dispatch(
            inputStream = """
                        5 3
                        1 1 E
                        RFRFRFRF
                        
                        3 2 N
                        FRRFLLFFRRFLL
                        
                        0 3 W
                        LLFFFLFLFL
                    """.trimIndent().byteInputStream(),
            outputStream = ByteArrayOutputStream()
        )

        verify(robotHiveMind, times(3)).moveRobot(any(), any(), any())
    }

    //Full integration test
    @Test
    fun `given reader succeeds, when dispatch, then lines written per robot`() {
        val dispatcher = MarsTrafficDispatcher(
            reader = EarthStationReader(WorldGridParser(), StartingPointParser()),
            robotHiveMind = RobotHiveMind()
        )
        val outputStream = ByteArrayOutputStream()
        dispatcher.dispatch(
            inputStream = """
                        5 3
                        1 1 E
                        RFRFRFRF
                        
                        3 2 N
                        FRRFLLFFRRFLL
                        
                        0 3 W
                        LLFFFLFLFL
                    """.trimIndent().byteInputStream(),
            outputStream = outputStream
        )

        val string = String(outputStream.toByteArray())
        assertThat(string).isEqualTo(
            """
                1 1 E
                3 3 N LOST
                2 3 S
                
            """.trimIndent()
        )
    }
}