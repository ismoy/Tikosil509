package domain.interfaces

import domain.entities.Sales
import domain.resultData.ResultData

interface ISendSalesRepository {
    suspend fun sendSales(sales: Sales,authToken:String,userIdTypeSelected:String):ResultData<Sales>
}