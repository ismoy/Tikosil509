package data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlagsDataModel(
    @SerialName("png")
    val png: String,
    @SerialName("svg")
    val svg: String
)