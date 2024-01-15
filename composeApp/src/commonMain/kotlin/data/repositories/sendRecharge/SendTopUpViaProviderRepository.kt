package data.repositories.sendRecharge

import data.network.KtorInstance
import domain.entities.RechargeErrorResponse
import domain.entities.RechargeSuccessResponse
import domain.interfaces.ISendTopUpViaProviderRepositoy
import domain.resultData.ResultData
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.http.formUrlEncode
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class SendTopUpViaProviderRepository:ISendTopUpViaProviderRepositoy {
    private val client = KtorInstance.client
    @OptIn(InternalAPI::class)
    override suspend fun sendTopUpViaProvider(
        idProduct: Int,
        destination: String
    ): ResultData<RechargeSuccessResponse> {
        val parameters = Parameters.build {
            append("id_product", idProduct.toString())
            append("destination", destination)
        }.formUrlEncode()
        return try {
            val response: HttpResponse = client.post("https://www.tikonsil509.com/api/send_recharge") {
                header("Authorization", "Bearer yT32cWocNpldrR=9ERnAvCtg/aon&D")
                contentType(ContentType.Application.FormUrlEncoded)
                body = parameters
            }
            val responseBody = response.bodyAsText()

            val jsonResponse = Json.parseToJsonElement(responseBody).jsonObject
            val status = jsonResponse["status"]?.jsonPrimitive?.content

            when {
                status == "success" -> {
                    val successResponse = Json.decodeFromString<RechargeSuccessResponse>(responseBody)
                    ResultData.Success(successResponse)
                }
                jsonResponse.containsKey("message") -> {
                    val errorResponse = Json.decodeFromString<RechargeErrorResponse>(responseBody)
                    ResultData.Error(Exception(errorResponse.message))
                }
                else -> ResultData.Error(Exception("Respuesta desconocida"))
            }
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }
}