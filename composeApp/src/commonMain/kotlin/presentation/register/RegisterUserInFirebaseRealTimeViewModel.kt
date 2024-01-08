package presentation.register

import BaseViewModel
import domain.entities.FirebaseResponseRegister
import domain.entities.UserResponse
import domain.entities.Users
import domain.resultData.ResultData
import domain.useCase.register.RegisterUserInFirebaseRealTimeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterUserInFirebaseRealTimeViewModel(private val registerUserInFirebaseRealTimeUseCase: RegisterUserInFirebaseRealTimeUseCase):BaseViewModel() {
    private val _registerUserInFirebaseRealTimeState = MutableStateFlow<UserResponse?>(null)
    val registerUserInFirebaseRealTimeState = _registerUserInFirebaseRealTimeState.asStateFlow()
    private val _errorRegisterUserInFirebaseRealTime = MutableStateFlow<String?>(null)
    val errorRegisterUserInFirebaseRealTime = _errorRegisterUserInFirebaseRealTime.asStateFlow()

    fun registerUserInFirebaseRealTime(userId:String,authToken:String, users: Users){
        viewModelScope.launch {
            when(val result =registerUserInFirebaseRealTimeUseCase(userId, authToken,users)){
                is ResultData.Success-> _registerUserInFirebaseRealTimeState.emit(result.data)
                is ResultData.Error-> _errorRegisterUserInFirebaseRealTime.emit(result.exception.message)
            }
        }
    }
}