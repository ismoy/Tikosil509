package utils

class FormValidator(private val mConstant: Constants) {

    fun isFormValid(firstName: String, lastName: String, email: String, phone: String, password: String, confirmPassword: String): Boolean =
         firstName.isNotEmpty() && mConstant.validateOnlyLetter(firstName) &&
                lastName.isNotEmpty() && mConstant.validateOnlyLetter(lastName) &&
                email.isNotEmpty() && mConstant.validateEmail(email) &&
                phone.isNotEmpty() && mConstant.validateLengthNumberPhone(phone) &&
                password.isNotEmpty() && mConstant.validateLongitudePassword(password) &&
                confirmPassword == password

}


