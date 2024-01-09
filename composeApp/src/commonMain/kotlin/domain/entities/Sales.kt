package domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Sales(
    @SerialName("codecountry")
    val codeCountry:String?=null,
    val country:String?=null,
    val date:String?=null,
    val description:String?=null,
    val currency:String?=null,
    val email:String?=null,
    val firstname:String?=null,
    val idUser:String?=null,
    @SerialName("id_product")
    val idProduct:Int?=null,
    val image:String?=null,
    val lastname:String?=null,
    val phone:String?=null,
    val role:Int?=null,
    val salesPrice:String?=null,
    val salesPriceFee:String?=null,
    val salesPriceSubtotal:String?=null,
    val status:Int?=null,
    val subtotal:Double?=null,
    val token:String?=null,
    @SerialName("typerecharge")
    val typeRecharge:String?=null)