package uk.co.origamibits.redbadger.business

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Before

import org.junit.Test
import uk.co.origamibits.redbadger.traffic.MarsTrafficDispatcher

@ExperimentalStdlibApi
class AsStringMoveRobotsUseCaseTest {

    private lateinit var useCase: AsStringMoveRobotsUseCase

    @Before
    fun setUp() {
    }

    @Test
    fun `given dispatcher fails, when execute, then return error`() {
        val dispatcher: MarsTrafficDispatcher = mock()
        val exception = IllegalArgumentException()
        given(dispatcher.dispatch(any(), any())).willThrow(exception)
        useCase = AsStringMoveRobotsUseCase(dispatcher)
        runBlocking {
            val output = useCase.execute(
                """
                5 3
                1 1 E
                RFRFRFRF
                
                3 2 N
                FRRFLLFFRRFLL
                
                0 3 W
                LLFFFLFLFL
                """.trimIndent()
            )
            assertThat(output).isEqualTo(AsStringMoveRobotsUseCase.Result.Error(exception))
        }
    }

    @Test
    fun `given dispatcher fails, when execute, then return success`() {
        val dispatcher: MarsTrafficDispatcher = mock()
        given(dispatcher.dispatch(any(), any())).willThrow(IllegalArgumentException())
        useCase = AsStringMoveRobotsUseCase(dispatcher)
        runBlocking {
            val output = useCase.execute(
                """
                5 3
                1 1 E
                RFRFRFRF
                
                3 2 N
                FRRFLLFFRRFLL
                
                0 3 W
                LLFFFLFLFL
                """.trimIndent()
            )
            assertThat(output).isEqualTo(
                """
                1 1 E
                3 3 N LOST
                2 3 S
                
            """.trimIndent()
            )
        }
    }
}