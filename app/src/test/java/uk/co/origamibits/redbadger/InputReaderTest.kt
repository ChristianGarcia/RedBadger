package uk.co.origamibits.redbadger

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Before
import org.junit.Test
import uk.co.origamibits.redbadger.reader.DefaultInputReader
import uk.co.origamibits.redbadger.reader.InputReader

class InputReaderTest {


    private lateinit var reader: InputReader

    @Before
    fun setUp() {
        reader = DefaultInputReader()
    }

    @Test
    fun `given no world grid available, when read, then fail`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy {
                reader.read("".byteInputStream(), NO_OP)
            }
    }

    @Test
    fun `given world grid single value, when read, then fail`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy {
                reader.read("5".byteInputStream(), NO_OP)
            }
    }

    @Test
    fun `given world grid non-numeric values, when read, then fail`() {

        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy {
                reader.read("5 b".byteInputStream(), NO_OP)
            }
    }

    @Test
    fun `given negative world grid values, when read then fail`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy {
                reader.read("-5 1".byteInputStream(), NO_OP)
            }
    }

    @Test
    fun `given valid world grid values, when read then expect x-y values`() {
        assertThat(reader.read("0 0".byteInputStream(), NO_OP).x).isEqualTo(0)
        assertThat(reader.read("0 0".byteInputStream(), NO_OP).y).isEqualTo(0)

        assertThat(reader.read("5 3".byteInputStream(), NO_OP).x).isEqualTo(5)
        assertThat(reader.read("5 3".byteInputStream(), NO_OP).y).isEqualTo(3)
    }

    @Test
    fun `given no robot instructions, when read, then perform no operations`() {
        var robotCount = 0

        reader.read("5 3".byteInputStream()) { _, _ -> robotCount++ }

        assertThat(robotCount).isEqualTo(0)
    }

    @Test
    fun `given robot entry with no instructions, when read, then perform no operations`() {
        var robotCount = 0
        val input = """
    |5 3
    |1 1 E
    |""".trimMargin()
        reader.read(input.byteInputStream()) { _, _ -> robotCount++ }

        assertThat(robotCount).isEqualTo(0)
    }

    @Test
    fun `given robot entry with missing orientation, when read, then ignore robot`() {
        var robotCount = 0
        val input = """
    |5 3
    |1 1
    |RFRFRFRF""".trimMargin()
        reader.read(input.byteInputStream()) { _, _ -> robotCount++ }

        assertThat(robotCount).isEqualTo(0)
    }

    companion object {
        private val NO_OP: (String, String) -> Unit = { _, _ -> }
    }
}