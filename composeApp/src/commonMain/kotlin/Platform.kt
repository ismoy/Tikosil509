import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

expect val ApplicationDispatcher: CoroutineDispatcher

open class BaseViewModel {
    private val viewModelJob = SupervisorJob()
    protected val viewModelScope = CoroutineScope(ApplicationDispatcher + viewModelJob)

    fun clearViewModel() {
        viewModelJob.cancel()
    }
}