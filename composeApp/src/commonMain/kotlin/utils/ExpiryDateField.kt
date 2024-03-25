package utils

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate

@Composable
fun ExpiryDateField(onMonthChanged: (String) -> Unit,onYearChanged:(String)->Unit) {
    val (month, setMonth) = remember { mutableStateOf("") }
    val (year, setYear) = remember { mutableStateOf("") }
    val (errorMessage, setErrorMessage) = remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    Row(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = month,
            onValueChange = {
                if (it.length <= 2) {
                    setMonth(it)
                    onMonthChanged(it)
                    if (it.length == 2) focusManager.moveFocus(FocusDirection.Right)
                }
            },
            label = { Text("Month") },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.width(8.dp)) // Espaciado entre los campos

        OutlinedTextField(
            value = year,
            onValueChange = {
                if (it.length <= 2) {
                    setYear(it)
                    onYearChanged(it)
                    if (it.length == 2) focusManager.clearFocus()
                }
            },
            label = { Text("Año") },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }



}
