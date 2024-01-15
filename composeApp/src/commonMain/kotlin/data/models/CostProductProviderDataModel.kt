package data.models

import kotlinx.serialization.Serializable

@Serializable
data class CostProductProviderDataModel (
    val priceReceiver:String,
    val operatorName:String,
    val priceSales:String,
    val nameMoneyCountryReceiver:String,
    val nameMoneyCountrySale:String,
    val idProduct:Long,
    val country:String,
    val formatPrice:String,
    val idKey: String
)