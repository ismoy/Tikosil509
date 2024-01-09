package domain.useCase.getUser

import data.repositories.getUsers.GetUserRepository
import domain.entities.Users
import domain.resultData.ResultData

class GetUserUseCase(private val getUserRepository: GetUserRepository) {
    suspend operator fun invoke(userId:String,idToken:String):ResultData<Users> =
        getUserRepository.getUser(userId, idToken)
}