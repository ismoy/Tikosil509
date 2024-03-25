package data.dataSource

import data.repositories.payment.StripePaymentRepository
import domain.entities.StripePayment
import domain.useCase.payment.StripePaymentUseCase
import presentation.payment.StripePaymentViewModel

class CreatePayment {
   private val stripePaymentViewModel by lazy { StripePaymentViewModel(
       StripePaymentUseCase(
       StripePaymentRepository()
   )
   ) }

}