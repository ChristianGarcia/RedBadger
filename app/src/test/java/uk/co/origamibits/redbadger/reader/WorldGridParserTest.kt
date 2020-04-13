package uk.co.origamibits.redbadger.reader

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.Before
import org.junit.Test

class WorldGridParserTest {

    private lateinit var parser: WorldGridParser

    @Before
    fun setUp() {
        parser = WorldGridParser()
    }

    @Test
    fun `given no world grid available, when read, then fail`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy {
                parser.parse("")
            }
    }

    @Test
    fun `given world grid single value, when read, then fail`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy {
                parser.parse("5")
            }
    }

    @Test
    fun `given world grid non-numeric values, when read, then fail`() {

        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy {
                parser.parse("5 b")
            }
    }

    @Test
    fun `given negative world grid values, when read then fail`() {
        assertThatExceptionOfType(IllegalArgumentException::class.java)
            .isThrownBy {
                parser.parse("-5 1")
            }
    }

    @Test
    fun `given valid world grid values, when read then expect x-y values`() {
        assertThat(parser.parse("0 0").x).isEqualTo(0)
        assertThat(parser.parse("0 0").y).isEqualTo(0)

        assertThat(parser.parse("5 3").x).isEqualTo(5)
        assertThat(parser.parse("5 3").y).isEqualTo(3)

    }
}