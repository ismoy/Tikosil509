package domain.entities

data class Product(
    val nameProduct:String,
    val amount: Float,
    val email:String,
    val companyName:String,
    val phoneNumber:String,
    val receiverAmount:String,
    val idProduct:Long,
    val userId:String,
    val firstName:String,
    val lastName:String,
    val role:Int,
    val tokenUser:String,
    val tokenAdmin:String,
    val date:String,
    val status:Int,
    val countryName:String,
    val imageUrl:String,
    val soldTopUp: Float,
    val subTotal:String,
    val currently:String
)