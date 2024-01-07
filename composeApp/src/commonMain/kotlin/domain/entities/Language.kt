package domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Language(
    @SerialName("iso639_1")
    val iso6391: String?=null,
    @SerialName("iso639_2")
    val iso6392: String?=null,
    @SerialName("name")
    val name: String?=null,
    @SerialName("nativeName")
    val nativeName: String?=null
)