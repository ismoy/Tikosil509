package domain.interfaces

import domain.entities.Users
import domain.resultData.ResultData

interface IUpdateProfileRepository {
    suspend fun updateProfile(userId:String, authToken:String, users: Users):ResultData<Users>
}