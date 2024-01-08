package domain.interfaces

import domain.entities.FirebaseResponseRegister
import domain.resultData.ResultData

interface IRegisterRepository {
    suspend fun registerUser(email:String,password:String):ResultData<FirebaseResponseRegister>
}