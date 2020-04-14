package uk.co.origamibits.redbadger.ui

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import uk.co.origamibits.redbadger.business.AsStringMoveRobotsUseCase
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalStdlibApi
class RobotTrafficViewModel @Inject constructor(
    private val useCase: AsStringMoveRobotsUseCase
) : ViewModel() {

    private val submitClicks = MutableLiveData<Unit>()
    fun onSubmit() {
        submitClicks.value = Unit
    }

    private val input = MutableLiveData<String>()
    fun onInputChanged(text: CharSequence) {
        input.value = text.toString()
    }

    private val submittableInput: Flow<String> = submitClicks.asFlow().withLatestFrom(input.asFlow()) { _, input -> input }
    val output: LiveData<String> = submittableInput.distinctUntilChanged().flatMapLatest { output(it) }.asLiveData()

    private fun output(input: String): Flow<String> = flow {
        emit(
            when (val result = useCase.execute(input)) {
                is AsStringMoveRobotsUseCase.Result.Success -> result.data
                is AsStringMoveRobotsUseCase.Result.Error -> "Error: ${result.exception.localizedMessage}"
            }
        )
    }

    @Deprecated("//This will be added in future versions of Flow: https://github.com/Kotlin/kotlinx.coroutines/issues/1498")
    private fun <A, B : Any, R> Flow<A>.withLatestFrom(other: Flow<B>, transform: suspend (A, B) -> R): Flow<R> = flow {
        coroutineScope {
            val latestB = AtomicReference<B?>()
            val outerScope = this
            launch {
                try {
                    other.collect { latestB.set(it) }
                } catch (e: CancellationException) {
                    outerScope.cancel(e) // cancel outer scope on cancellation exception, too
                }
            }
            collect { a: A ->
                latestB.get()?.let { b -> emit(transform(a, b)) }
            }
        }
    }

}