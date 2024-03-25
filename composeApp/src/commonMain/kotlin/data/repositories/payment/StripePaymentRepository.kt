package data.repositories.payment

import data.network.KtorInstance
import domain.entities.StripePayment
import domain.entities.StripePaymentResponse
import domain.interfaces.IStripePaymentRepository
import domain.resultData.ResultData
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.serialization.json.Json
import utils.Constants.BASE_URL_TIKONSIL
import utils.Constants.END_POINT_STRIPE

class StripePaymentRepository:IStripePaymentRepository {
    private val client = KtorInstance.client
    @OptIn(InternalAPI::class)
    override suspend fun createPayment(stripePayment: StripePayment): ResultData<StripePaymentResponse> {
        return try {
            val response: HttpResponse = client.post(BASE_URL_TIKONSIL + END_POINT_STRIPE) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer yT32cWocNpldrR=9ERnAvCtg/aon&D")
                body = stripePayment
            }
            val responseBodyString = response.bodyAsText()
            ResultData.Success(Json.decodeFromString<StripePaymentResponse>(responseBodyString))
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }

}