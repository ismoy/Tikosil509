package presentation.updateProfile

import BaseViewModel
import domain.entities.Users
import domain.resultData.ResultData
import domain.useCase.updateProfile.UpdateProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UpdateProfileViewModel(private val updateProfileUseCase: UpdateProfileUseCase):BaseViewModel() {

    private var _updateProfileResponse = MutableStateFlow<Users?>(null)
    val updateProfileResponse: StateFlow<Users?> = _updateProfileResponse
    private var _errorProfileResponse = MutableStateFlow("")
    val errorProfileResponse:StateFlow<String> = _errorProfileResponse

    fun updateProfile(userId:String,authToken:String,users: Users){
        viewModelScope.launch {
            when (val result = updateProfileUseCase(userId, authToken, users)){
                is ResultData.Success -> _updateProfileResponse.emit(result.data)
                is ResultData.Error -> _errorProfileResponse.emit(result.exception.message.toString())
            }
        }
    }
}