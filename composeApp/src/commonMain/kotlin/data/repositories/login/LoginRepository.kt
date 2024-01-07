package data.repositories.login

import data.network.KtorInstance
import domain.entities.FirebaseErrorResponse
import domain.entities.FirebaseLoginRequest
import domain.entities.FirebaseResponseLogin
import domain.interfaces.ILoginRepository
import domain.resultData.ResultData
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import utils.Constants

class LoginRepository:ILoginRepository {
    private val client = KtorInstance.client
    @OptIn(InternalAPI::class)
    override suspend fun loginUser(
        email: String,
        password: String
    ): ResultData<FirebaseResponseLogin> {
        return try {
            val responseLogin:HttpResponse = client.post(Constants.GOOGLE_API_SIG_IN){
                contentType(ContentType.Application.Json)
                body = FirebaseLoginRequest(email,password,true)
            }
            if (responseLogin.status == HttpStatusCode.OK){
                ResultData.Success(responseLogin.body())
            }else{
                val errorResponse = responseLogin.body<FirebaseErrorResponse>()
                ResultData.Error(Exception(errorResponse.error.message))
            }
        }catch (e:Exception){
            ResultData.Error(e)
        }
    }
}