package ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.repositories.getCountry.GetCountriesRepository
import data.repositories.login.LoginRepository
import domain.entities.Country
import domain.entities.SharedCountriesData
import domain.entities.SharedData
import domain.useCase.getCountry.GetCountriesUseCase
import domain.useCase.login.LoginUseCase
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.getCountry.GetCountriesViewModel
import presentation.login.LoginViewModel
import ui.forgotPassword.ForgotPasswordScreen
import ui.home.HomeScreen
import ui.register.RegisterScreen
import utils.Constants
import utils.Constants.PRIMARY_COLOR
import utils.DataCountryShared
import utils.DataRepository
import utils.FormValidatorLogin
import utils.GlobalBottomSheet

class  LoginScreen:Screen {
    @OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        var email by remember { mutableStateOf("belizairesmoy72@gmail.com") }
        var password by remember { mutableStateOf("123456") }
        val passwordVisible by remember { mutableStateOf(false) }
        val navigator = LocalNavigator.currentOrThrow
        val loginUserViewModel by lazy { LoginViewModel(LoginUseCase(LoginRepository())) }
        val countriesViewModel by lazy { GetCountriesViewModel(GetCountriesUseCase(GetCountriesRepository())) }
        var countries by remember { mutableStateOf<List<Country>>(emptyList()) }
        val miShape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomEnd = 0.dp,
            bottomStart = 130.dp
        )
        val mConstants by remember { mutableStateOf(Constants) }
        val formValidator by remember { mutableStateOf(FormValidatorLogin(mConstants)) }
        val isFormValid =  remember(email,password) {
            formValidator.isFormValid(email,password)
        }
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()
        var title by remember { mutableStateOf("") }
        var message by remember { mutableStateOf("") }
        var icon by remember { mutableStateOf(Icons.Default.Check) }

        LaunchedEffect(true) {
            countriesViewModel.getCountries()
            countriesViewModel.countriesResponse.collect{
                if (it.isNotEmpty()){
                    countries = it
                    DataCountryShared.setSharedCountriesData(SharedCountriesData(it))
                }

            }
        }

        val loginUser = {
            scope.launch {
                loginUserViewModel.loginUser(email, password)
                loginUserViewModel.loginResponse.collect{response->
                    if (response != null) {
                        navigator.push(HomeScreen())
                        SharedData(response).let { DataRepository.setSharedData(it) }
                    }
                }
            }
        }
        scope.launch {
            loginUserViewModel.errorLoginResponse.collect{errorResponse->
                if (errorResponse != null) {
                    if (errorResponse.isNotEmpty()){
                        sheetState.show()
                        title= "Error!"
                        message= errorResponse
                        icon = Icons.Default.Close
                    }
                }
            }
        }

        GlobalBottomSheet(
            sheetState = sheetState,
            title = title,
            message = message,
            icon = icon,
            action = "",
            onDismiss = {scope.launch { sheetState.hide() }}
        ){
                Box(
                    modifier = Modifier
                        .size(600.dp, 150.dp)
                        .background(color = Color(PRIMARY_COLOR), shape = miShape)
                        .fillMaxWidth()
                        .padding(bottom = 50.dp)
                ) {

                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    // Email OutlinedTextField
                    OutlinedTextField(
                        value = email,
                        onValueChange = {email = it},
                        label = { Text("Ingrese el email ") },
                        modifier = Modifier.fillMaxWidth()
                            .height(60.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    // Password OutlinedTextField
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Ingrese la contraseña") },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else
                            PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                            .height(60.dp)
                    )
                    //Forgot Password
                    Box(modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 8.dp, start = 1.dp, end = 1.dp, bottom = 5.dp)
                        .clickable { navigator.push(ForgotPasswordScreen()) }) {
                        Text(
                            text = "Forgot password?",
                            color = Color.Black
                        )
                    }
                    // Create account
                    if (countries.isNotEmpty()){
                        Box(modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 15.dp)
                            .clickable {navigator.push(RegisterScreen()) }) {
                            Text("New here? Create an account!")
                        }
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)) {
                        Button(onClick = { loginUser() },
                            enabled =isFormValid,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(PRIMARY_COLOR)),
                            shape = RoundedCornerShape(20),
                            modifier = Modifier.fillMaxWidth()
                                .height(50.dp),) {
                            Text(
                                text = "iniciar session",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }


                    }



                }


        }

    }

}