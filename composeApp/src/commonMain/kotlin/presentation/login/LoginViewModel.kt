package presentation.login

import BaseViewModel
import domain.entities.FirebaseResponseLogin
import domain.resultData.ResultData
import domain.useCase.login.LoginUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase):BaseViewModel() {
    private val _loginResponse = MutableStateFlow<FirebaseResponseLogin?>(null)
    val loginResponse: StateFlow<FirebaseResponseLogin?> = _loginResponse
    private val _errorLoginResponse = MutableStateFlow<String?>("")
    val errorLoginResponse: StateFlow<String?> = _errorLoginResponse

    fun loginUser(email:String,password:String){
        viewModelScope.launch {
            when(val result = loginUseCase(email, password)){
                is ResultData.Success->
                    _loginResponse.emit(result.data)

                is ResultData.Error ->
                    _errorLoginResponse.emit(result.exception.message?:"Unknown error")


            }
        }
    }

}