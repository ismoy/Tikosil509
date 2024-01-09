package data.repositories.getSales

import data.network.KtorInstance
import domain.entities.Sales
import domain.interfaces.IGetSalesRepository
import domain.resultData.ResultData
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import utils.Constants

class GetSalesRepository:IGetSalesRepository {
    private val client = KtorInstance.client
    override suspend fun getSales(authToken: String): ResultData<Map<String, Sales>> {
        return try {
            val response: HttpResponse =
                client.get("${Constants.FIREBASE_REALTIME_API}Sales.json?auth=${authToken}")
            val salesMap = response.body<Map<String, Sales>>()
            ResultData.Success(salesMap)
        } catch (e: Exception) {
            ResultData.Error(e)
        }

    }
}