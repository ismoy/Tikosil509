package domain.useCase.login

import data.repositories.login.LoginRepository
import domain.entities.FirebaseResponseLogin
import domain.resultData.ResultData

class LoginUseCase(private val repository: LoginRepository) {
    suspend operator fun invoke(email:String,password:String):ResultData<FirebaseResponseLogin> =
        repository.loginUser(email, password)
}