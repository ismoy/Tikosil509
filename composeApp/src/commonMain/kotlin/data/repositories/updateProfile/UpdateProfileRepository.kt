package data.repositories.updateProfile

import data.network.KtorInstance
import domain.entities.Users
import domain.interfaces.IUpdateProfileRepository
import domain.resultData.ResultData
import io.ktor.client.call.body
import io.ktor.client.request.patch
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import utils.Constants

class UpdateProfileRepository:IUpdateProfileRepository {
    private val client = KtorInstance.client
    @OptIn(InternalAPI::class)
    override suspend fun updateProfile(userId:String, authToken:String, users: Users): ResultData<Users> {
        return try {
            val request: HttpResponse = client.patch("${Constants.FIREBASE_REALTIME_API}/Users/$userId.json?auth=$authToken"){
                contentType(ContentType.Application.Json)
                body = users
            }
            ResultData.Success(request.body())
        }catch (e:Exception){
            ResultData.Error(e)
        }
    }
}