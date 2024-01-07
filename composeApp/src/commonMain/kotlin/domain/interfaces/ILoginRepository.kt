package domain.interfaces

import domain.entities.FirebaseResponseLogin
import domain.resultData.ResultData

interface ILoginRepository {
    suspend fun loginUser(email:String,password:String):ResultData<FirebaseResponseLogin>
}