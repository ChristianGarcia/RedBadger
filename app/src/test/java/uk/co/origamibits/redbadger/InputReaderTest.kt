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
                reader.read("".byteInputStream())
            }
    }

    @Test
    fun `given world grid single value, when read, then fail`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy {
                reader.read("5".byteInputStream())
            }
    }

    @Test
    fun `given world grid non-numeric values, when read, then fail`() {

        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy {
                reader.read("5 b".byteInputStream())
            }
    }

    @Test
    fun `given negative world grid values, when read then fail`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy {
                reader.read("-5 1".byteInputStream())
            }
    }

    @Test
    fun `given valid world grid values, when read then expect x-y values`() {
        assertThat(reader.read("0 0".byteInputStream()).x).isEqualTo(0)
        assertThat(reader.read("0 0".byteInputStream()).y).isEqualTo(0)

        assertThat(reader.read("5 3".byteInputStream()).x).isEqualTo(5)
        assertThat(reader.read("5 3".byteInputStream()).y).isEqualTo(3)
    }
}