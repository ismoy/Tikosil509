package domain.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RechargeSuccessResponse(
    val status: String,
    @SerialName("error_code")
    val errorCode: Int?=null,
    @SerialName("recharge_id")
    val rechargeId: Int?=null,
    val destination: String?=null,
    @SerialName("reference_code")
    val referenceCode: String?=null,
    val balance: Double?=null)
@Serializable
data class RechargeErrorResponse(
    val status: String,
    val message: String?=null,
    @SerialName("error_code")
    val errorCode: Int?=null
)
@Serializable
data class StripePaymentResponse(
    val status: String ,
    val message:String
)