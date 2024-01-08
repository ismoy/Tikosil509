package domain.useCase.register

import domain.entities.FirebaseResponseRegister
import domain.interfaces.IRegisterRepository
import domain.resultData.ResultData

class RegisterUseCase(private val repository:IRegisterRepository) {
    suspend operator fun invoke(email:String,password:String):ResultData<FirebaseResponseRegister> =
        repository.registerUser(email, password)
}