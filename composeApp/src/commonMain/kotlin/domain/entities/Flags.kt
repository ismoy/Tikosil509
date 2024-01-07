package domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Flags(
    @SerialName("png")
    val png: String,
    @SerialName("svg")
    val svg: String
)