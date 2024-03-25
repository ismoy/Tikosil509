package domain.useCase.payment

import data.repositories.payment.StripePaymentRepository
import domain.entities.StripePayment
import domain.entities.StripePaymentResponse
import domain.resultData.ResultData

class StripePaymentUseCase(private val repository: StripePaymentRepository) {
    suspend operator fun invoke(stripePayment: StripePayment):ResultData<StripePaymentResponse> =
        repository.createPayment(stripePayment)
}