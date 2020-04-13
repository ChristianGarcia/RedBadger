package uk.co.origamibits.redbadger

import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Before
import org.junit.Test

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
}