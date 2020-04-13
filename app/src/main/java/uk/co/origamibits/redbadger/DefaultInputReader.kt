package uk.co.origamibits.redbadger

import java.io.InputStream
import java.nio.charset.Charset
import java.nio.charset.CharsetDecoder

class DefaultInputReader : InputReader {

    private val decoder: CharsetDecoder by lazy { Charset.defaultCharset().newDecoder() }


    override fun read(inputStream: InputStream) {

    }

}
