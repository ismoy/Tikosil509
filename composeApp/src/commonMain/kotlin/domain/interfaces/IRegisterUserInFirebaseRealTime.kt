package domain.interfaces

import domain.entities.UserResponse
import domain.entities.Users
import domain.resultData.ResultData

interface IRegisterUserInFirebaseRealTime {
    suspend fun registerUserInFirebaseRealTime(userId:String,authToken:String,users: Users):ResultData<UserResponse>
}