package utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun CVVField(cvv: String, onCvvChanged: (String) -> Unit, cardIssuer: String) {
    val maxLength = if (cardIssuer == "American Express") 4 else 3
    val cvvVisualTransformation = PasswordVisualTransformation()

    OutlinedTextField(
        value = cvv,
        onValueChange = { if (it.length <= maxLength) onCvvChanged(it)  },
        visualTransformation = cvvVisualTransformation,
        label = { Text("CVV")},
        modifier = Modifier.fillMaxWidth()
    )
}
