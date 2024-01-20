@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

import io.ktor.http.encodeURLParameter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual val ApplicationDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class WhatsAppLinkOpener {
    actual fun openWhatsApp(phoneNumber: String, message: String) {
        val url = "https://api.whatsapp.com/send?phone=$phoneNumber&text=${message.encodeURLParameter()}"
        UIApplication.sharedApplication.openURL(NSURL.URLWithString(url)!!)
    }
}

actual object PlatformInitializer {
    actual fun initialize(context: Any) {
    }
}