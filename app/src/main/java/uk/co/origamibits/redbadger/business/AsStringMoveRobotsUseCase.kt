package uk.co.origamibits.redbadger.business

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uk.co.origamibits.redbadger.traffic.MarsTrafficDispatcher
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class AsStringMoveRobotsUseCase @Inject constructor(
    private val marsTrafficDispatcher: MarsTrafficDispatcher
) {

    @ExperimentalStdlibApi
    suspend fun execute(input: String): Result =
        withContext(Dispatchers.IO) {
            try {
                val outputStream = ByteArrayOutputStream()
                marsTrafficDispatcher.dispatch(input.byteInputStream(), outputStream)
                Result.Success(String(outputStream.toByteArray()))
            } catch (e: Throwable) {
                Result.Error(e)
            }
        }

    sealed class Result {
        data class Success(val data: String) : Result()
        data class Error(val exception: Throwable) : Result()
    }
}