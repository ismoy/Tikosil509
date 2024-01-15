package presentation.updateSoldTopUpUser

import BaseViewModel
import domain.entities.UpdateTopUpResponse
import domain.resultData.ResultData
import domain.useCase.updateSoldTopUpUser.UpdateSoldTopUpUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpdateSoldTopUpUserViewModel(private val updateSolDTopUpUserUseCase:UpdateSoldTopUpUserUseCase):BaseViewModel() {

    private var _updateSoldTopUpUserResponse = MutableStateFlow<UpdateTopUpResponse?>(null)
    val updateSoldTopUpUserResponse  = _updateSoldTopUpUserResponse.asStateFlow()
    private var _errorUpdateSoldTopUpUser = MutableStateFlow("")
    val errorUpdateSoldTopUpUser  = _errorUpdateSoldTopUpUser.asStateFlow()

    fun updateSoldTopUpUser(userId:String,idToken:String,valueTopUpSelected:Float){
        viewModelScope.launch {
            when(val result = updateSolDTopUpUserUseCase(userId, idToken, valueTopUpSelected)){
                is ResultData.Success -> _updateSoldTopUpUserResponse.emit(result.data)
                is ResultData.Error -> _errorUpdateSoldTopUpUser.emit(result.exception.message.toString())
            }
        }
    }

}