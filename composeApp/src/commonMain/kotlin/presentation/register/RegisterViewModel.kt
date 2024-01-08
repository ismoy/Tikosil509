package presentation.register

import BaseViewModel
import domain.entities.FirebaseResponseRegister
import domain.resultData.ResultData
import domain.useCase.register.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerUseCase: RegisterUseCase):BaseViewModel() {
    private val _registerUserState = MutableStateFlow<FirebaseResponseRegister?>(null)
    val registerUserState = _registerUserState.asStateFlow()
    private val _errorRegisterUser = MutableStateFlow<String?>(null)
    val errorRegisterUser = _errorRegisterUser.asStateFlow()

    fun registerUser(email:String,password:String){
        viewModelScope.launch {
            when (val result = registerUseCase(email, password)) {
                is ResultData.Success -> _registerUserState.emit(result.data)
                is ResultData.Error -> _errorRegisterUser.emit(result.exception.message)
            }
        }
    }

}