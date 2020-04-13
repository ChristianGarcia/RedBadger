package uk.co.origamibits.redbadger.traffic

import uk.co.origamibits.redbadger.reader.EarthStationReader
import java.io.InputStream

class MarsTrafficDispatcher(
    private val reader: EarthStationReader
) {
    fun dispatch(inputStream: InputStream) {
        reader.read(inputStream) { _, _, _ -> }
    }
}