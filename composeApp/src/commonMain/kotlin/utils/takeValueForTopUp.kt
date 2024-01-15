package utils

fun takeValueForTopUp(first: String): Float? {
    val regexPattern = """\d+\.\d+""".toRegex()
    val matchResults = regexPattern.findAll(first)
    val extractedValues = matchResults.map { it.value }.toList()

    return if (extractedValues.isNotEmpty()) {
        val targetValue = extractedValues.last()
        targetValue.toFloatOrNull()
    } else {
        println("No se encontró un valor decimal en el string.")
        null
    }
}

