package domain.interfaces

import domain.entities.Users
import domain.resultData.ResultData

interface IGetUsersRepository {
    suspend fun getUser(userId:String,idToken:String):ResultData<Users>
}