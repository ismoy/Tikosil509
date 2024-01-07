package data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrencyDataModel(
    @SerialName("code")
    val code: String,
    @SerialName("name")
    val name: String,
    @SerialName("symbol")
    val symbol: String
)