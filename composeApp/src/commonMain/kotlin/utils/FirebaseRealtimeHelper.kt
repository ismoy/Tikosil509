package utils

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.navigator.Navigator
import domain.entities.FirebaseResponseRegister
import domain.entities.Users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import presentation.register.RegisterUserInFirebaseRealTimeViewModel

class FirebaseRealtimeHelper {

    fun setUserDataInFirebaseRealtime(
        scope: CoroutineScope,
        response: FirebaseResponseRegister,
        registerUserInFirebaseRealTimeViewModel: RegisterUserInFirebaseRealTimeViewModel,
        countrySelected: Comparable<Nothing>,
        firstName: String,
        lastName: String,
        email: String,
        phone: String,
        password: String,
        selectedCallingCode: String,
        navigator: Navigator
    ) {
        val users = Users(response.userId, countrySelected.toString(), "", firstName, lastName,
            email, "$selectedCallingCode$phone", 1, password, password, 0F, 0F,
            0F, 0F, 1, "")
        scope.launch {
            registerUserInFirebaseRealTimeViewModel.registerUserInFirebaseRealTime(response.userId, response.idToken, users)
            registerUserInFirebaseRealTimeViewModel.registerUserInFirebaseRealTimeState.collect{
                 navigator.pop()
            }
        }
    }
}
