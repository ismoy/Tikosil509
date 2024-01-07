package data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegionalBlocDataModel(
    @SerialName("acronym")
    val acronym: String,
    @SerialName("name")
    val name: String,
    @SerialName("otherNames")
    val otherNames: List<String>?=null,
    @SerialName("otherAcronyms")
    val otherAcronyms: List<String>?=null

)