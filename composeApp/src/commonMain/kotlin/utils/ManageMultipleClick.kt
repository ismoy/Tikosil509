package utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ManageMultipleClick(
    private val delayMillis: Long,
    private val coroutineScope: CoroutineScope
) {
    private var job: Job? = null

    fun manageMultipleClickVoyagerTransition(action: () -> Unit) {
        job?.cancel()
        job = coroutineScope.launch {
            delay(delayMillis)
            action()
        }
    }
}
