import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.tikonsil.tikonsil509.theme.AppTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.login.LoginScreen

@Composable
fun App() = AppTheme {
  Navigator(screen = LoginScreen()){ navigator->
      SlideTransition(navigator)
  }
}