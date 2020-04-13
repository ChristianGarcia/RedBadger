package uk.co.origamibits.redbadger.traffic

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Before
import org.junit.Test
import uk.co.origamibits.redbadger.reader.EarthStationReader

class MarsTrafficDispatcherTest {

    private lateinit var dispatcher: MarsTrafficDispatcher

    private val reader: EarthStationReader = mock()

    @Before
    fun setUp() {
        dispatcher = MarsTrafficDispatcher()
    }

    @Test
    fun `given reader fails, when dispatch, then fail`() {
        given(reader.read(any(), any())).willThrow(IllegalArgumentException())

        assertThatExceptionOfType(IllegalArgumentException::class.java).isThrownBy {
            dispatcher.dispatch()
        }
    }
}