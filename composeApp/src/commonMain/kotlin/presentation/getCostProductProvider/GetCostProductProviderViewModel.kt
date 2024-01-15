package presentation.getCostProductProvider

import BaseViewModel
import domain.entities.CostProductProvider
import domain.resultData.ResultData
import domain.useCase.getCostProductProvider.GetCostProductProviderUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GetCostProductProviderViewModel(private val getCostProductProviderUseCase: GetCostProductProviderUseCase):BaseViewModel() {
   private val _getCostProductProvider = MutableStateFlow<List<CostProductProvider>>(emptyList())
    val getCostProductProvider = _getCostProductProvider.asStateFlow()
    private val _errorGetCostProductProvider = MutableStateFlow<String?>(null)
    val errorGetCostProductProvider = _errorGetCostProductProvider.asStateFlow()

    fun loadCostProductProvider(idToken:String){
        viewModelScope.launch {
            when(val result = getCostProductProviderUseCase(idToken)){
                is ResultData.Success-> _getCostProductProvider.emit(result.data.values.toList())
                is ResultData.Error-> _errorGetCostProductProvider.emit(result.exception.message)
            }
        }
    }
}