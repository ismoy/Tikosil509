package data.repositories.sendSales

import data.network.KtorInstance
import domain.entities.Sales
import domain.interfaces.ISendSalesRepository
import domain.resultData.ResultData
import io.ktor.client.call.body
import io.ktor.client.request.patch
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import utils.Constants

class SendSalesRepository:ISendSalesRepository {
    private val client = KtorInstance.client
    @OptIn(InternalAPI::class)
    override suspend fun sendSales(
        sales: Sales,
        authToken: String,
        userIdTypeSelected: String
    ): ResultData<Sales> {
        return try {
            val request: HttpResponse = client.patch("${Constants.FIREBASE_REALTIME_API}/Sales/$userIdTypeSelected.json?auth=$authToken"){
                contentType(ContentType.Application.Json)
                body = sales
            }
            ResultData.Success(request.body())
        }catch (e:Exception){
            ResultData.Error(e)
        }

    }
}