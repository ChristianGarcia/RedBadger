package uk.co.origamibits.redbadger.reader

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Before
import org.junit.Test
import uk.co.origamibits.redbadger.model.StartingPoint
import uk.co.origamibits.redbadger.model.WorldGrid

class DefaultInputReaderTest {

    private lateinit var reader: InputReader

    private val worldGridParser: WorldGridParser = mock()
    private val startingPointParser: StartingPointParser = mock()

    @Before
    fun setUp() {
        reader = DefaultInputReader(worldGridParser, startingPointParser)
    }

    @Test
    fun `given parsing world grid fails, when read, then fail`() {
        given(worldGridParser.parse(any())).willThrow(IllegalArgumentException())
        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy {
                reader.read("".byteInputStream(), NO_OP)
            }
    }

    @Test
    fun `given parsing world grid succeeds, when read then expect x-y values`() {
        given(worldGridParser.parse(any())).willCallRealMethod()

        reader.read("0 0".byteInputStream()) { grid, _, _ ->
            assertThat(grid.x).isEqualTo(0)
            assertThat(grid.y).isEqualTo(0)
        }
        reader.read("5 3".byteInputStream()) { grid, _, _ ->
            assertThat(grid.x).isEqualTo(5)
            assertThat(grid.y).isEqualTo(3)
        }

    }

    @Test
    fun `given no robot instructions, when read, then perform no operations`() {
        var robotCount = 0

        reader.read("5 3".byteInputStream()) { _, _, _ -> robotCount++ }

        assertThat(robotCount).isEqualTo(0)
    }

    @Test
    fun `given robot entry with invalid starting point, when read, then ignore robot`() {

        given(startingPointParser.parse(any())).willReturn(null)

        var robotCount = 0
        val input = """
    |5 3
    |1 1
    |RFRFRFRF""".trimMargin()

        reader.read(input.byteInputStream()) { _, _, _ -> robotCount++ }

        assertThat(robotCount).isEqualTo(0)
    }

    @Test
    fun `given robot entry with no instructions, when read, then perform no operations`() {
        given(startingPointParser.parse(any())).willCallRealMethod()

        var robotCount = 0
        val input = """
    |5 3
    |1 1 E
    |""".trimMargin()
        reader.read(input.byteInputStream()) { _, _, _ -> robotCount++ }

        assertThat(robotCount).isEqualTo(0)
    }

    @Test
    fun `given robot entry with valid orientation and instructions, when read, then perform operations`() {

        given(worldGridParser.parse(any())).willCallRealMethod()
        given(startingPointParser.parse(any())).willCallRealMethod()

        var robotCount = 0
        val input = """
    |5 3
    |1 1 E
    |RFRFRFRF""".trimMargin()
        reader.read(input.byteInputStream()) { _, _, _ -> robotCount++ }

        assertThat(robotCount).isEqualTo(1)
    }

    companion object {
        private val NO_OP: (WorldGrid, StartingPoint, String) -> Unit = { _, _, _ -> }
    }
}