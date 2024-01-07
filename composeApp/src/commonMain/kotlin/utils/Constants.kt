package utils

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToLong

object Constants  {
    private const val API_KEY ="AIzaSyAa-024D1R4oYi5jBPZLjSSL-Xh8g1-obY"
    const val GOOGLE_API_SIGNUP = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=$API_KEY"
    const val FIREBASE_REALTIME_API = "https://tikonsil509-d45d7-default-rtdb.firebaseio.com/"
    const val GOOGLE_API_SIG_IN = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=$API_KEY"
    const val PRIMARY_COLOR = 0xFFF9A826
    const val CENTRAL_COLOR = 0xFFAEDEF4
    const val COLOR_HISTORY = 0xFFF2F7FF
    const val BASE_URL_TIKONSIL = "https://tikonsil509.com/"
    const val END_POINT_STRIPE = "api/payment/stripe"
    const val KEY_STRIPE = "Bearer yT32cWocNpldrR=9ERnAvCtg/aon&D"
    const val LAPOULA ="LAPOULA"
    const val MONCASH = "MONCASH"
    const val NATCASH = "NATCASH"
    const val TOPUP = "TOPUP"
    const val PHONE_NUMBER_WHATSAPP ="+56935707687"
    fun validateEmail(email: String): Boolean {
        val emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
        return email.matches(emailRegex.toRegex())
    }

    fun validateOnlyLetter(data: String): Boolean {
        val matchesPattern = "[a-zA-Z ]+"
        return data.matches(matchesPattern.toRegex())
    }

    fun validateLengthNumberPhone(number: String): Boolean {
        return number.length <= 9
    }

    fun validateLongitudePassword(password: String): Boolean {
        return password.length >= 6
    }
    fun getCurrentDateTime(): String {
        val currentMoment = Clock.System.now()
        val dateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())

        return "${dateTime.dayOfMonth.toString().padStart(2, '0')}/" +
                "${dateTime.monthNumber.toString().padStart(2, '0')}/" +
                "${dateTime.year} " +
                "${dateTime.hour.toString().padStart(2, '0')}:" +
                "${dateTime.minute.toString().padStart(2, '0')}:" +
                dateTime.second.toString().padStart(2, '0')
    }
    fun removeSlashesAndSpaces(date: String): String {
        return date.replace("/", "").replace(" ", "").replace(":","")
    }
    fun calculateMonCash(priceMonCash: Double, inputValue: Double): String {
        val countValueMonCash = inputValue / priceMonCash
        return countValueMonCash.roundTo2DecimalPlaces().toString()
    }

    private fun Double.roundTo2DecimalPlaces(): Double {
        return (this * 100).roundToLong() / 100.0
    }



}