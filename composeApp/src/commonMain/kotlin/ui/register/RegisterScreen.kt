package ui.register

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.repositories.register.RegisterRepository
import data.repositories.register.RegisterUserInFirebaseRealTimeRepository
import domain.useCase.register.RegisterUseCase
import domain.useCase.register.RegisterUserInFirebaseRealTimeUseCase
import kotlinx.coroutines.launch
import presentation.register.RegisterUserInFirebaseRealTimeViewModel
import presentation.register.RegisterViewModel
import utils.Constants
import utils.Constants.PRIMARY_COLOR
import utils.DataCountryShared
import utils.FirebaseRealtimeHelper
import utils.FormValidator
import utils.GlobalBottomSheet

class RegisterScreen:Screen {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        var title by remember { mutableStateOf("") }
        var message by remember { mutableStateOf("") }
        var icon by remember { mutableStateOf(Icons.Default.Check) }
        var action by remember { mutableStateOf("") }
        val scope = rememberCoroutineScope()
        val countries by remember { mutableStateOf( DataCountryShared.getSharedCountriesData()) }
        var expanded by remember { mutableStateOf(false) }
        var selectedCountry by remember { mutableStateOf("") }
        val filteredItems by derivedStateOf {
            if (selectedCountry.isEmpty()) {
                countries?.data
            } else {
                countries?.data?.filter { it.name.toString().contains(selectedCountry, ignoreCase = true) }
            }
        }
        var changeHintText by remember { mutableStateOf("Cangando...") }

        if (countries?.data !=null){
            changeHintText = "Buscar por país"
        }
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        val scrollState = rememberScrollState()
        val mConstants by remember { mutableStateOf(Constants) }
        val formValidator by remember { mutableStateOf(FormValidator(mConstants)) }
        val isFormValid =  remember(firstName,lastName,email,phone,password,confirmPassword) {
            formValidator.isFormValid(firstName,lastName,email,phone,password,confirmPassword)
        }
        var selectedAreaCode by remember { mutableStateOf<List<String>?>(null) }


        val navigator = LocalNavigator.currentOrThrow
        val registerViewModel by lazy { RegisterViewModel(RegisterUseCase(RegisterRepository()))}
        val registerUserInFirebaseRealTimeViewModel by lazy {
            RegisterUserInFirebaseRealTimeViewModel(RegisterUserInFirebaseRealTimeUseCase(RegisterUserInFirebaseRealTimeRepository()))
        }
         val firebaseRealtimeHelper = FirebaseRealtimeHelper()



        //TODO(create user Auth)
        val registerUser = {
            scope.launch {
                registerViewModel.registerUser(email, password)
                registerViewModel.registerUserState.collect{response->
                    if (response != null) {
                        firebaseRealtimeHelper.setUserDataInFirebaseRealtime(scope,response,registerUserInFirebaseRealTimeViewModel,selectedCountry,
                            firstName,lastName,email,phone,password,"$selectedAreaCode",navigator)
                    }
                }
            }
            scope.launch {
                registerViewModel.errorRegisterUser.collect{error->
                  sheetState.show()
                    title = "Error!"
                    if (error != null) {
                        message = "El usuario ya existe"
                    }
                    icon = Icons.Default.Error
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
            Scaffold (
                topBar = {
                    TopAppBar(
                        title = { Text("Registrar usuarios") },
                        contentColor = Color(0xFFFfff),
                        navigationIcon = {
                            IconButton(onClick ={navigator.pop()} ) {
                                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Regresar")
                            }
                        },
                        backgroundColor = Color(PRIMARY_COLOR)
                    )
                }
            ){

                MaterialTheme {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding( horizontal = 16.dp)
                            .verticalScroll(scrollState)
                    ) {
                        OutlinedTextField(
                            value = selectedCountry,
                            onValueChange = {
                                selectedCountry = it
                                expanded = it.isNotEmpty()
                            },
                            label = { Text(changeHintText) },
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 15.dp)
                            ,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)

                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                focusedIndicatorColor = Color(PRIMARY_COLOR)
                            )
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 200.dp)
                        ) {
                                if (filteredItems?.isEmpty() == true) {
                                    DropdownMenuItem(onClick = {}) {
                                        Text("No se encontraron coincidencias")
                                    }
                                } else {
                                    filteredItems?.forEach { country ->
                                        DropdownMenuItem(onClick = {
                                            selectedCountry = country.name.toString()
                                            selectedAreaCode = country.callingCodes
                                            expanded = false
                                        }) {
                                            Text(country.name.toString())
                                        }
                                    }
                                }

                        }
                        Spacer(modifier = Modifier.padding(top = 10.dp))
                        // OutlinedTextField para firstName
                        Text("FirstName")
                        Box {
                            OutlinedTextField(
                                value = firstName,
                                onValueChange = { firstName = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.Transparent,
                                    focusedIndicatorColor = Color(PRIMARY_COLOR)
                                )
                            )
                            if (!Constants.validateOnlyLetter(firstName) && firstName.isNotEmpty()){
                                Text("Please enter a valid FirstName", color = Color.Red)
                            }else{
                                Text("")
                            }
                        }
                        Spacer(modifier = Modifier.padding(top = 10.dp))
                        // OutlinedTextField para lastName
                        Text("LastName")
                        Box {
                            OutlinedTextField(
                                value = lastName,
                                onValueChange = { lastName = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.Transparent,
                                    focusedIndicatorColor = Color(PRIMARY_COLOR)
                                )
                            )
                            if (!Constants.validateOnlyLetter(lastName) && lastName.isNotEmpty()){
                                Text("Please enter a valid LastName", color = Color.Red)
                            }else{
                                Text("")
                            }
                        }

                        Spacer(modifier = Modifier.padding(top = 10.dp))
                        // OutlinedTextField para email
                        Text("Email")
                        Box {
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it; {
                                }},
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.Transparent,
                                    focusedIndicatorColor = Color(PRIMARY_COLOR)
                                )
                            )

                            if (!Constants.validateEmail(email) && email.isNotEmpty()){
                                Text("Please enter a valid email", color = Color.Red)
                            }else{
                                Text("")
                            }
                        }


                        Spacer(modifier = Modifier.padding(top = 10.dp))
                        Text("Phone", modifier = Modifier.padding(start = 90.dp))
                        Row(modifier = Modifier
                            .fillMaxWidth()
                        ) {
                            Box(modifier = Modifier.weight(0.25F)
                                .padding(end = 8.dp)
                                .height(50.dp)
                            ) {
                                OutlinedTextField(
                                    modifier = Modifier
                                    ,
                                    readOnly = true,
                                    value = "+${selectedAreaCode?.get(0)?:""}",
                                    onValueChange = {},
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = Color.Black,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent
                                    )
                                )
                            }
                            Box(modifier = Modifier.weight(0.75F)
                                .padding(start = 4.dp)
                                .height(50.dp)
                            ) {
                                OutlinedTextField(
                                    modifier = Modifier,
                                    value = phone,
                                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                                    onValueChange = { newValue ->
                                        if (newValue.length <= 9) {
                                            phone = newValue
                                        }
                                    },
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = Color.Transparent,
                                        focusedIndicatorColor = Color(PRIMARY_COLOR)
                                    )
                                )
                                if (!Constants.validateLengthNumberPhone(phone) && phone.isNotEmpty()){
                                    Text("Please enter a valid phone number", color = Color.Red)
                                }else{
                                    Text("")
                                }
                            }

                        }
                        Spacer(modifier = Modifier.padding(top = 10.dp))
                        // OutlinedTextField para password
                        Text("Password")
                        Box {
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.Transparent,
                                    focusedIndicatorColor = Color(PRIMARY_COLOR)
                                )
                            )
                            if (!Constants.validateLongitudePassword(password) && password.isNotEmpty()){
                                Text("Password must be at least 6 characters", color = Color.Red)
                            }else{
                                Text("")
                            }
                        }

                        Spacer(modifier = Modifier.padding(top = 10.dp))
                        // OutlinedTextField para confirmPassword
                        Text("Confirm password")
                        Box {
                            OutlinedTextField(
                                value = confirmPassword,
                                onValueChange = { confirmPassword = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.Transparent,
                                    focusedIndicatorColor = Color(PRIMARY_COLOR)
                                )
                            )
                            if (password != confirmPassword && confirmPassword.isNotEmpty()){
                                Text("Password do not match", color = Color.Red)
                            }else{
                                Text("")
                            }
                        }

                        Box(modifier = Modifier.fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp)) {
                            Button(onClick = {
                                registerUser()
                            },
                                enabled =isFormValid,
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(PRIMARY_COLOR)),
                                shape = RoundedCornerShape(20),
                                modifier = Modifier.fillMaxWidth()
                                    .height(50.dp),) {
                                Text(
                                    text = "Registrarse",
                                    color = Color.White,
                                    fontSize = 18.sp
                                )
                            }
                        }

                    }

                }
            }
        }
    }
}