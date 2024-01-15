package domain.useCase.updateSoldTopUpUser

import data.repositories.updateSoldTopUpUser.UpdateSoldTopUpUserRepository
import domain.entities.UpdateTopUpResponse
import domain.resultData.ResultData

class UpdateSoldTopUpUserUseCase(private val updateSoldTopUpUserRepository: UpdateSoldTopUpUserRepository) {
    suspend operator fun invoke(userId:String,idToken:String,valueTopUpSelected:Float):ResultData<UpdateTopUpResponse> =
        updateSoldTopUpUserRepository.updateSoldTopUpUser(userId, idToken, valueTopUpSelected)
}