package utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CardNumberVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = if (text.text.length >= 4) {
            "*".repeat(text.text.length - 4) + text.text.takeLast(4)
        } else {
            text.text
        }

        return TransformedText(AnnotatedString(transformedText), OffsetMapping.Identity)
    }
}
