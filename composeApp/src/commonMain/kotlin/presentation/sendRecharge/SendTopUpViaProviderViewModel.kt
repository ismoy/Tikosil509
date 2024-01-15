package presentation.sendRecharge

import BaseViewModel
import domain.entities.RechargeSuccessResponse
import domain.resultData.ResultData
import domain.useCase.sendRecharge.SendTopUpViaProviderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SendTopUpViaProviderViewModel(private val sendTopUpViaProviderUseCase: SendTopUpViaProviderUseCase):BaseViewModel() {
    private var _sendTopUpViaProviderResponse = MutableStateFlow<RechargeSuccessResponse?>(null)
    val sendTopUpViaProviderResponse: StateFlow<RechargeSuccessResponse?> = _sendTopUpViaProviderResponse
    private var _errorSendTopUpViaProvider = MutableStateFlow("")
    val errorSendTopUpViaProvider:StateFlow<String> = _errorSendTopUpViaProvider

    fun sendTopUpViaProvider(idProduct:Int,destination:String){
        viewModelScope.launch {
            when(val result = sendTopUpViaProviderUseCase(idProduct, destination)){
                is ResultData.Success -> _sendTopUpViaProviderResponse.emit(result.data)
                is ResultData.Error -> _errorSendTopUpViaProvider.emit(result.exception.message.toString())
            }
        }
    }
}