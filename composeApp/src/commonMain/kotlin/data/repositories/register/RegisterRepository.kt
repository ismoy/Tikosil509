package data.repositories.register

import data.network.KtorInstance
import domain.entities.FirebaseRegisterRequest
import domain.entities.FirebaseResponseRegister
import domain.interfaces.IRegisterRepository
import domain.resultData.ResultData
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import utils.Constants.GOOGLE_API_SIGNUP

class RegisterRepository:IRegisterRepository {
    private val client = KtorInstance.client
    @OptIn(InternalAPI::class)
    override suspend fun registerUser(
        email: String,
        password: String
    ): ResultData<FirebaseResponseRegister> {
        return try {
            val response:HttpResponse = client.post(GOOGLE_API_SIGNUP){
                contentType(ContentType.Application.Json)
                body = FirebaseRegisterRequest(email,password,true)
            }
            ResultData.Success(response.body())
        }catch (e:Exception){
            ResultData.Error(e)
        }
    }
}