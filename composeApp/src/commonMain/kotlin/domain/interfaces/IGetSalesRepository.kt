package domain.interfaces

import domain.entities.Sales
import domain.resultData.ResultData

interface IGetSalesRepository {
    suspend fun getSales(authToken:String):ResultData<Map<String,Sales>>
}