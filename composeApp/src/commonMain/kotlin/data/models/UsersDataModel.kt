package data.models

import kotlinx.serialization.Serializable

@Serializable
data class UsersDataModel(
    val id:String? =null,
    val countryselected:String? =null,
    val countrycode:String?=null,
    val firstname:String?=null,
    val lastname:String?=null,
    val email:String?=null,
    val phone:String?=null,
    val role:Int?=null,
    val password:String?=null,
    val confirmpassword:String?=null,
    val soldmoncash:Float?=null,
    val soltopup:Float?=null,
    val soldnatcash:Float?=null,
    val soldlapoula:Float?=null,
    val status:Int?=null,
    val image:String?=null
)