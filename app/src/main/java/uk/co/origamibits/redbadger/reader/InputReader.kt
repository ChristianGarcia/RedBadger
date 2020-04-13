package uk.co.origamibits.redbadger.reader

import uk.co.origamibits.redbadger.model.WorldGrid
import java.io.InputStream

interface InputReader {

    fun read(inputStream: InputStream): WorldGrid

}
