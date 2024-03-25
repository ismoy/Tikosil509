package utils

fun detectCardIssuer(cardNumber: String): String
    = when {
        cardNumber.startsWith("34") || cardNumber.startsWith("37") -> "American Express"
        cardNumber.startsWith("4") -> "Visa"
        cardNumber.startsWith("51") || cardNumber.startsWith("52") ||
                cardNumber.startsWith("53") || cardNumber.startsWith("54") ||
                cardNumber.startsWith("55") -> "MasterCard"
        cardNumber.startsWith("50") || cardNumber.startsWith("56") ||
                cardNumber.startsWith("57") || cardNumber.startsWith("58") -> "Maestro"
        else -> "Unknown"

}
