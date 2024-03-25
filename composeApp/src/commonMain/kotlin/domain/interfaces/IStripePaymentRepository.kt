package domain.interfaces

import domain.entities.StripePayment
import domain.entities.StripePaymentResponse
import domain.resultData.ResultData

interface IStripePaymentRepository {
    suspend fun createPayment(stripePayment: StripePayment):ResultData<StripePaymentResponse>
}