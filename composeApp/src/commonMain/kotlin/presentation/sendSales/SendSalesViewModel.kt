package presentation.sendSales

import BaseViewModel
import domain.entities.Sales
import domain.resultData.ResultData
import domain.useCase.sendSales.SendSalesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SendSalesViewModel(private val sendSalesUseCase: SendSalesUseCase):BaseViewModel() {
    private val _sendSalesResponse = MutableStateFlow<Sales?>(null)
    val sendSalesResponse = _sendSalesResponse.asStateFlow()
    private val _errorMessageSales = MutableStateFlow("")
    val errorMessageSales = _errorMessageSales.asStateFlow()

    fun sendSales(sales: Sales,authToken:String,userIdTypeSelected:String){
        viewModelScope.launch {
            when (val result = sendSalesUseCase(sales, authToken,userIdTypeSelected)) {
                is ResultData.Success -> _sendSalesResponse.emit(result.data)
                is ResultData.Error -> _errorMessageSales.emit(result.exception.message.toString())

            }
        }
    }
}