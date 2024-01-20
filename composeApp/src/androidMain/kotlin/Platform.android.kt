@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val ApplicationDispatcher: CoroutineDispatcher = Dispatchers.Main

actual object PlatformInitializer {
    actual fun initialize(context: Any) {
        WhatsAppLinkOpener.initialize(context as Context)
    }
}
@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class WhatsAppLinkOpener {
    companion object {
        private var appContext: Context? = null

        fun initialize(context: Context) {
            appContext = context
        }
    }

    actual fun openWhatsApp(phoneNumber: String, message: String) {
        appContext?.let { context ->
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("whatsapp://send?phone=${phoneNumber}&text=$message")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, "WhatsApp no está instalado en este dispositivo.", Toast.LENGTH_LONG).show()
            }
        } ?: run {
            println("Error: Contexto de WhatsAppLinkOpener no inicializado.")
        }
    }
}
