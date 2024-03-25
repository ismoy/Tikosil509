package presentation.payment

import BaseViewModel
import domain.entities.StripePayment
import domain.entities.StripePaymentResponse
import domain.resultData.ResultData
import domain.useCase.payment.StripePaymentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StripePaymentViewModel(private val stripePaymentUseCase: StripePaymentUseCase):BaseViewModel() {
    private var _paymentResult = MutableStateFlow<StripePaymentResponse?>(null)
    val paymentResult = _paymentResult.asStateFlow()
    private var _errorMessage = MutableStateFlow("")
    val errorMessage =_errorMessage.asStateFlow()

    fun createStripePayment(stripePayment: StripePayment){
        viewModelScope.launch {
            when(val result = stripePaymentUseCase(stripePayment)){
                is ResultData.Success -> _paymentResult.emit(result.data)
                is ResultData.Error -> {
                    _errorMessage.emit(result.exception.message.toString())
                    print("valorError ${result.exception.message}")
                }
            }
        }
    }
}