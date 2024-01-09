package presentation.getSales

import BaseViewModel
import domain.entities.Sales
import domain.resultData.ResultData
import domain.useCase.getSales.GetSalesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GetSalesViewModel(private val getSalesUseCase: GetSalesUseCase):BaseViewModel() {
    private val _getSalesResponse = MutableStateFlow<Map<String, Sales>?>(null)
    val getSalesResponse : StateFlow<Map<String, Sales>?> = _getSalesResponse
    private val _errorGetSalesResponse = MutableStateFlow("")
    val errorGetSalesResponse: StateFlow<String> = _errorGetSalesResponse

    fun getSales(authTokens:String){
        viewModelScope.launch {
            when (val result = getSalesUseCase(authTokens)) {
                is ResultData.Success -> {
                    _getSalesResponse.emit(result.data)
                }
                is ResultData.Error -> {
                    _errorGetSalesResponse.emit(result.exception.message.toString())
                }
            }
        }
    }
}