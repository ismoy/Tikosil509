package domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StripePayment(
    @SerialName("card_number")
    val cardNumber:String,
    @SerialName("card_month")
    val cardMonth:String,
    @SerialName("card_year")
    val cardYear:String,
    @SerialName("card_cvv")
    val cardCvv: String,
    val amount:String
)
