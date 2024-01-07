import android.os.Build
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

actual val ApplicationDispatcher: CoroutineDispatcher = Dispatchers.Main
