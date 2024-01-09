package utils

import kotlin.math.roundToLong

fun Double.roundTo2DecimalPlaces(): Double {
    return (this * 100).roundToLong() / 100.0
}

