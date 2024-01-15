package utils

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class ProgressBarState {
    var isVisible by mutableStateOf(false)
        private set

    fun show() {
        isVisible = true
    }

    fun hide() {
        isVisible = false
    }
}
@Composable
fun CircleProgressBar(state: ProgressBarState) {
    if (state.isVisible) {
        CircularProgressIndicator(
            color = Color.White,
            modifier = Modifier.size(15.dp)
        )
    }
}
