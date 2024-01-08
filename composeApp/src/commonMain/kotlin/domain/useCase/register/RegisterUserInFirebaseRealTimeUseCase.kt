package domain.useCase.register

import data.repositories.register.RegisterUserInFirebaseRealTimeRepository
import domain.entities.UserResponse
import domain.entities.Users
import domain.interfaces.IRegisterUserInFirebaseRealTime
import domain.resultData.ResultData

class RegisterUserInFirebaseRealTimeUseCase(private val repository:RegisterUserInFirebaseRealTimeRepository) {
    suspend operator fun invoke(userId:String,authToken:String,users: Users):ResultData<UserResponse> =
        repository.registerUserInFirebaseRealTime(userId, authToken, users)
}