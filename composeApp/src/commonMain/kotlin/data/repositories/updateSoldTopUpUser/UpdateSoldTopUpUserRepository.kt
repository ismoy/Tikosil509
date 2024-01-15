package data.repositories.updateSoldTopUpUser

import data.network.KtorInstance
import domain.entities.UpdateTopUpResponse
import domain.interfaces.IUpdateSoldTopUpUserRepository
import domain.resultData.ResultData
import io.ktor.client.call.body
import io.ktor.client.request.patch
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import utils.Constants

class UpdateSoldTopUpUserRepository:IUpdateSoldTopUpUserRepository {
    private val client = KtorInstance.client
    @OptIn(InternalAPI::class)
    override suspend fun updateSoldTopUpUser(
        userId: String,
        idToken: String,
        valueTopUpSelected: Float
    ): ResultData<UpdateTopUpResponse> {
        return try {
            val request: HttpResponse = client.patch("${Constants.FIREBASE_REALTIME_API}/Users/$userId.json?auth=$idToken"){
                contentType(ContentType.Application.Json)
                body = UpdateTopUpResponse(valueTopUpSelected)
            }
            ResultData.Success(request.body())
        }catch (e:Exception){
            ResultData.Error(e)
        }
    }
}