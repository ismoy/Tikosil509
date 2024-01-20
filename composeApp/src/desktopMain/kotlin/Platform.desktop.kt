@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val ApplicationDispatcher: CoroutineDispatcher = Dispatchers.Default

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class WhatsAppLinkOpener {
    actual fun openWhatsApp(phoneNumber: String, message: String) {
    }
}

actual object PlatformInitializer {
    actual fun initialize(context: Any) {
    }
}