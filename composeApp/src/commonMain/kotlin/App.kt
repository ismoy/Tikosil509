import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.tikonsil.tikonsil509.theme.AppTheme
import ui.login.LoginScreen

@Composable
fun App() = AppTheme {
  Navigator(screen = LoginScreen())
}