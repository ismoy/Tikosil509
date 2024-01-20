@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

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
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class WhatsAppLinkOpener() {
    fun openWhatsApp(phoneNumber: String, message: String)
}
expect object PlatformInitializer {
    fun initialize(context: Any)
}