package domain.interfaces

import domain.entities.CostProductProvider
import domain.resultData.ResultData

interface ICostProductProviderRepository {
    suspend fun getCostProductProvider(idToken:String):ResultData<Map<String,CostProductProvider>>
}