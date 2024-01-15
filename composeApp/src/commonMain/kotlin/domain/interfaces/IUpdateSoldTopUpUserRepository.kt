package domain.interfaces

import domain.entities.UpdateTopUpResponse
import domain.resultData.ResultData

interface IUpdateSoldTopUpUserRepository {
    suspend fun updateSoldTopUpUser(userId:String,idToken:String,valueTopUpSelected:Float):
            ResultData<UpdateTopUpResponse>
}