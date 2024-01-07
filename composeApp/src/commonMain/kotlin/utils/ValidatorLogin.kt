package utils

class FormValidatorLogin(private val mConstant: Constants) {
    fun isFormValid(email: String,password: String): Boolean =
         email.isNotEmpty() && mConstant.validateEmail(email) &&
                password.isNotEmpty() && mConstant.validateLongitudePassword(password)

}


