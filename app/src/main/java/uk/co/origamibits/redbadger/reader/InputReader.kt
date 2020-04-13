package uk.co.origamibits.redbadger.reader

import uk.co.origamibits.redbadger.model.StartingPoint
import uk.co.origamibits.redbadger.model.WorldGrid
import java.io.InputStream

interface InputReader {

    fun read(inputStream: InputStream, block: (WorldGrid, StartingPoint, String) -> Unit)

}
