package uk.co.origamibits.redbadger

import java.io.InputStream
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder

class DefaultInputReader : InputReader {

    private val decoder: CharsetDecoder by lazy { Charset.defaultCharset().newDecoder() }


    override fun read(inputStream: InputStream) {
        val worldLine = inputStream.reader().readLines().firstOrNull() ?: throw IllegalArgumentException()
        val (x, y) = try {
            worldLine.split(" ").let { it[0].toInt() to it[1].toInt() }
        } catch (e: IndexOutOfBoundsException) {
            throw IllegalArgumentException("Expected grid world with format <x> <y>'")
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException(e)
        }
    }

}
