package ui.sendRecharge

import BaseViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import data.dataSource.SendTopUpRecharge
import data.repositories.getCostProductProvider.GetCostProductProviderRepository
import data.repositories.sendSales.SendSalesRepository
import domain.entities.CostProductProvider
import domain.entities.CostInnoveritDetails
import domain.entities.Sales
import domain.useCase.getCostProductProvider.GetCostProductProviderUseCase
import domain.useCase.sendSales.SendSalesUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import presentation.getCostProductProvider.GetCostProductProviderViewModel
import presentation.sendSales.SendSalesViewModel
import ui.payment.PaymentMethodScreen
import utils.CircleProgressBar
import utils.Constants
import utils.Constants.PRIMARY_COLOR
import utils.Constants.TOPUP
import utils.DataCountryShared
import utils.DataRepository
import utils.GlobalBottomSheet
import utils.ManageMultipleClick
import utils.ProgressBarState
import utils.ValidatorSendRecharge
import utils.takeValueForTopUp

class SendRechargeScreen:Screen {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        var expanded by remember { mutableStateOf(false) }
        val navigator = LocalNavigator.currentOrThrow
        val countries by remember { mutableStateOf( DataCountryShared.getSharedCountriesData()) }
        var selectedCountry by remember { mutableStateOf("") }
        val filteredItems by derivedStateOf {
            if (selectedCountry.isEmpty()) {
                countries?.data
            } else {
                countries?.data?.filter { it.name.toString().contains(selectedCountry, ignoreCase = true) }
            }
        }
        var changeHintText by remember { mutableStateOf("Cangando...") }
        val tokenData by remember { mutableStateOf( DataRepository.getSharedData()) }
        if (countries?.data !=null){
            changeHintText = "Buscar por país"
        }
        var loadsCostInnoverit by remember { mutableStateOf<List<CostProductProvider>>(emptyList()) }
        var selectedCost by remember { mutableStateOf("") }
        var selectedPriceTopUp by remember { mutableStateOf(0F) }
        var selectedOperator by remember { mutableStateOf("") }
        var expandedCost by remember { mutableStateOf(false) }
        var selectedIdProduct by remember { mutableStateOf<Long?>(null) }
        var selectedCountryCode by remember { mutableStateOf(countries?.data?.firstOrNull()?.alpha2Code ?: "") }
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()
        var title by remember { mutableStateOf("") }
        var message by remember { mutableStateOf("") }
        var icon by remember { mutableStateOf(Icons.Default.Check) }
        var action by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }
        val mConstants by remember { mutableStateOf(Constants) }
        val formValidator by remember { mutableStateOf(ValidatorSendRecharge(mConstants)) }
        val isFormValid =  remember(selectedCountryCode,selectedIdProduct.toString(),phone) {
            formValidator.isFormValid(selectedCountryCode,selectedIdProduct.toString(),phone)
        }
        var selectedAreaCode by remember { mutableStateOf<List<String>?>(null) }
        var selectedFlag by remember { mutableStateOf<String?>(null) }
        val manageMultipleClick = remember { ManageMultipleClick(1000L, scope) }
        val sendTopUpRecharge by remember { mutableStateOf(SendTopUpRecharge()) }
        val getCostProductProviderViewModel by lazy { GetCostProductProviderViewModel(
            GetCostProductProviderUseCase(GetCostProductProviderRepository())
        ) }
        var btnText by remember { mutableStateOf("Send recharge") }
        val progressBarState = remember { ProgressBarState() }

        val clickSendTopUpRecharge ={
            btnText = ""
            progressBarState.show()
            sendTopUpRecharge.startPayment(topUpSelected = selectedPriceTopUp, operatorSelected = selectedOperator,
                subTotalSelected = "$selectedPriceTopUp", phone = "${selectedAreaCode}${phone}", idProductSelected = selectedIdProduct?:0,
                countrySelected = selectedCountry)
        }
        val sendSalesViewModel by lazy { SendSalesViewModel(SendSalesUseCase(SendSalesRepository())) }
        LaunchedEffect(true){
            getCostProductProviderViewModel.loadCostProductProvider(tokenData?.data?.idToken.toString())
            getCostProductProviderViewModel.getCostProductProvider.collect{listCostProduct->
                if (listCostProduct.isNotEmpty()){
                    loadsCostInnoverit = listCostProduct
                    selectedCost = listCostProduct.first().formatPrice
                    selectedIdProduct = listCostProduct.first().idProduct
                    selectedOperator = listCostProduct.first().operatorName
                    val valuePriceTopUp = takeValueForTopUp(selectedCost)
                    if (valuePriceTopUp != null) {
                        selectedPriceTopUp = valuePriceTopUp
                    }
                }
            }
        }

        scope.launch {
            sendTopUpRecharge.isTopUpSend.collect{result->
                val sales = Sales(selectedCountryCode,selectedCountry,
                    Constants.getCurrentDateTime(),null,"USD",sendTopUpRecharge.userInfoData.value?.data?.email,
                    sendTopUpRecharge.userInfoData.value?.data?.firstname, sendTopUpRecharge.userInfoData.value?.data?.id,selectedIdProduct?.toInt(),selectedFlag,
                    sendTopUpRecharge.userInfoData.value?.data?.lastname,phone, sendTopUpRecharge.userInfoData.value?.data?.role,
                    selectedPriceTopUp.toString(),null,null,1,selectedPriceTopUp.toString().toDouble(),null,selectedOperator)
                if (result.isNotEmpty()){
                    sheetState.show()
                    title = "Success"
                    message = "Gracias por su compra"
                    icon = Icons.Default.Check
                    action = TOPUP
                    progressBarState.hide()
                    btnText = "Send recharge"
                    sendSalesViewModel.sendSales(sales,sendTopUpRecharge.data.value?.data?.idToken.toString(),"${sendTopUpRecharge.userInfoData.value?.data?.id}${selectedIdProduct}${
                        Constants.removeSlashesAndSpaces(Constants.getCurrentDateTime())
                    }")
                }

            }
        }

        scope.launch {
            sendTopUpRecharge.isTopUpNotSend.collect{response->
                if (response.isNotEmpty()){
                    sheetState.show()
                    title = "Error!"
                    icon = Icons.Default.Close
                    message = response
                    progressBarState.hide()
                    btnText = "Send recharge"
                }
            }
        }
        scope.launch {
            sendTopUpRecharge.isNotEnoughBalanceTopUp.collect{
                if (it){
                    sheetState.show()
                    title = "Error!"
                    icon = Icons.Default.Close
                    message = "No tienes suficiente de fondos para realizar la compra"

                }else{
                    navigator.push(PaymentMethodScreen())
                }
            }
        }
        scope.launch {
            sendSalesViewModel.sendSalesResponse.collect{salesResponse->
              if (salesResponse!=null){
                  sendSalesViewModel.clearViewModel()
              }
            }
        }


        GlobalBottomSheet(
            sheetState = sheetState,
            title = title,
            message = message,
            icon = icon,
            action = action,
            onDismiss = {scope.launch { sheetState.hide() }}
        ){
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("SendRecharge") },
                        contentColor = Color.White,
                        navigationIcon = {
                            IconButton(onClick = {navigator.pop()}) {
                                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Regresar")
                            }
                        },
                        backgroundColor = Color(PRIMARY_COLOR),
                        elevation = 0.dp
                    )
                }
            ){
                MaterialTheme {
                    Column(modifier = Modifier.padding(15.dp)) {
                        Box {

                            OutlinedTextField(
                                value = selectedCountry,
                                onValueChange = {
                                    selectedCountry = it
                                    expanded = it.isNotEmpty()
                                    selectedCountryCode = ""
                                },
                                label = { Text(changeHintText) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 40.dp),
                                trailingIcon = {
                                    IconButton(onClick = { expanded = !expanded }) {
                                        Icon(Icons.Filled.ArrowDropDown, contentDescription = "Desplegar")
                                    }
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
                                            selectedCountryCode = country.alpha2Code.toString()
                                            selectedAreaCode = country.callingCodes
                                            selectedFlag = country.flags?.png
                                            expanded = false
                                        }) {
                                            Text(country.name.toString())
                                        }
                                    }
                                }

                            }
                        }


                        if (loadsCostInnoverit.isNotEmpty()) {
                            // Filtrar los datos para el nuevo país seleccionado
                            val filteredForSelectedCountry = loadsCostInnoverit.filter {
                                it.country == selectedCountryCode
                            }
                            // Lógica para habilitar/deshabilitar y actualizar el texto del OutlinedTextField
                            val isRecargaDisponible = filteredForSelectedCountry.isNotEmpty()
                            val label = if (isRecargaDisponible) "Seleccione un operador" else "No hay recargas disponibles para el país seleccionado"
                            val textFieldValue = if (isRecargaDisponible) selectedCost ?: "" else ""

                            val newLoadsCostInnoveritPair by derivedStateOf {
                                loadsCostInnoverit.filter { it.country == selectedCountryCode }
                                    .map {
                                        CostInnoveritDetails(
                                            formatPrice = it.formatPrice,
                                            idProduct = it.idProduct,
                                            operatorName = it.operatorName
                                        )
                                    }

                            }



                            Box(Modifier.clickable(enabled = isRecargaDisponible) { expandedCost = true }.padding(top = 10.dp)) {
                                OutlinedTextField(
                                    value = textFieldValue,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text(label) },
                                    modifier = Modifier.fillMaxWidth(),
                                    trailingIcon = {
                                        if (isRecargaDisponible) ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCost)
                                    },
                                    colors = TextFieldDefaults.textFieldColors(
                                        backgroundColor = Color.Transparent,
                                        focusedIndicatorColor = Color(PRIMARY_COLOR)
                                    ),
                                    enabled = isRecargaDisponible
                                )


                                DropdownMenu(
                                    expanded = expandedCost,
                                    onDismissRequest = { expandedCost = false },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(max = 200.dp)
                                ) {
                                    newLoadsCostInnoveritPair.forEach { item ->
                                        DropdownMenuItem(onClick = {
                                            val result = takeValueForTopUp(item.formatPrice)
                                            if (result != null) {
                                                selectedPriceTopUp = result
                                            }
                                            selectedIdProduct = item.idProduct
                                            selectedCost = item.formatPrice
                                            selectedOperator = item.operatorName
                                            expandedCost = false
                                        }) {
                                            Text(item.formatPrice)
                                        }
                                    }
                                }
                            }

                        }


                        Text("Phone", modifier = Modifier.padding(top = 10.dp))
                        Row(modifier = Modifier
                            .fillMaxWidth()
                        ) {
                            Box(modifier = Modifier.weight(0.25F)
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
                                        textColor = Color.Black,
                                        focusedIndicatorColor = Color(PRIMARY_COLOR),
                                        backgroundColor = Color.Transparent
                                    )
                                )

                            }
                            if (!Constants.validateLengthNumberPhone(phone) && phone.isNotEmpty()){
                                Text("Please enter a valid phone number", color = Color.Red)
                            }else{
                                Text("")
                            }
                        }

                        Spacer( modifier = Modifier.padding(top = 20.dp))
                        Button(onClick = {
                            manageMultipleClick.manageMultipleClickVoyagerTransition {
                                if (sendTopUpRecharge.userInfoData.value?.data?.role == 2){
                                    clickSendTopUpRecharge()
                                }
                            }
                        },
                            enabled = isFormValid,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(PRIMARY_COLOR)),
                            shape = RoundedCornerShape(20),
                            modifier = Modifier.fillMaxWidth()
                                .height(50.dp),) {
                            Text(
                                text =btnText ,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                            CircleProgressBar(state = progressBarState)

                        }
                    }


                }
            }
        }


    }
}