package uk.co.origamibits.redbadger.reader

import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import timber.log.Timber
import uk.co.origamibits.redbadger.UnitTestTree
import uk.co.origamibits.redbadger.model.Orientation
import uk.co.origamibits.redbadger.model.StartingPoint

class StartingPointParserTest {

    private lateinit var parser: StartingPointParser

    @Before
    fun setUp() {
        Timber.plant(UnitTestTree())
        parser = StartingPointParser()
    }

    @Test
    fun `given entry with missing orientation, when read, then return null`() {
        assertThat(parser.parse("1 1")).isNull()
    }

    @Test
    fun `given entry with non-numeric coordinates, when read, then return null`() {
        assertThat(parser.parse("a 1 E")).isNull()
    }

    @Test
    fun `given entry with negative x, when read, then return null`() {
        assertThat(parser.parse("-1 1 E")).isNull()
    }

    @Test
    fun `given entry with negative y, when read, then return null`() {
        assertThat(parser.parse("1 -1 E")).isNull()
    }

    @Test
    fun `given entry with valid values, when read, then return null`() {
        assertThat(parser.parse("1 1 E")).isEqualTo(
            StartingPoint(1, 1, Orientation.E)
        )
    }
}