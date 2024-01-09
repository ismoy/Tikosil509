package presentation.getUser

import BaseViewModel
import domain.entities.Users
import domain.resultData.ResultData
import domain.useCase.getUser.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GetUserViewModel(private val getUserUseCase: GetUserUseCase):BaseViewModel() {
    private val _users = MutableStateFlow<Users?>(null)
    val users = _users.asStateFlow()
    private var _errorUserResponse = MutableStateFlow("")
    val errorUserResponse  = _errorUserResponse.asStateFlow()

    fun getUsers(userId:String,idToken:String){
        viewModelScope.launch {
            when (val result = getUserUseCase(userId, idToken)) {
                is ResultData.Success -> _users.emit(result.data)
                is ResultData.Error -> _errorUserResponse .emit(result.exception.message.toString())
            }
        }
    }
}