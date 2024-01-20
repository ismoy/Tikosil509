package utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import utils.Constants.LAPOULA
import utils.Constants.MONCASH
import utils.Constants.NATCASH
import utils.Constants.PRIMARY_COLOR

@Composable
fun ChipGroupSingleChoice(onChipSelected: (String) -> Unit) {
    val chipLabels = listOf(MONCASH, LAPOULA, NATCASH)
    var selectedChip by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 15.dp)
        ,
        horizontalArrangement = Arrangement.Center // Centra los elementos en el Row
    ) {
        chipLabels.forEach { label ->
            Chip(
                text = label,
                onClick = { selectedChip = if (selectedChip == label) "" else label
                           onChipSelected(selectedChip)
                          },
                modifier = Modifier.padding(4.dp),
                enabled = selectedChip == "" || selectedChip == label
            )
        }
    }
}

@Composable
fun Chip(onClick: () -> Unit, text: String, modifier: Modifier = Modifier, enabled: Boolean) {
    Surface(
        modifier = modifier
            .clickable(enabled = enabled, onClick = onClick)
            .alpha(if (enabled) 1f else 0.2f), // Reduce la opacidad si no está habilitado
        shape = RoundedCornerShape(16.dp),
        color = Color(PRIMARY_COLOR)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(8.dp),
            color = Color.White,
            fontSize = 16.sp

        )
    }
}
