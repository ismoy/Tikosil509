package utils

class ValidatorSendRecharge(private val mConstant:Constants) {
    fun isFormValid(country: String, idProduct: String, phone: String): Boolean {
        return country.isNotEmpty() && idProduct.isNotEmpty() &&
                phone.isNotEmpty() && mConstant.validateLengthNumberPhone(phone)
    }
}
