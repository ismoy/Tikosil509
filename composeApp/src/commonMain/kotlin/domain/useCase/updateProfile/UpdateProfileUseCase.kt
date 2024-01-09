package domain.useCase.updateProfile

import data.repositories.updateProfile.UpdateProfileRepository
import domain.entities.Users
import domain.resultData.ResultData

class UpdateProfileUseCase(private val updateProfileRepository: UpdateProfileRepository) {
    suspend operator fun invoke (userId:String,authToken:String, users: Users):ResultData<Users> =
        updateProfileRepository.updateProfile(userId, authToken, users)
}