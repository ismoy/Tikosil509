package ui.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.dataSource.CreatePayment
import data.repositories.payment.StripePaymentRepository
import domain.entities.StripePayment
import domain.useCase.payment.StripePaymentUseCase
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.payment.StripePaymentViewModel
import utils.CVVField
import utils.CardForm
import utils.CardNumberVisualTransformation
import utils.Constants
import utils.ExpiryDateField
import utils.GlobalBottomSheet
import utils.detectCardIssuer

class PaymentMethodScreen:Screen {
    @OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()
        var title by remember { mutableStateOf("") }
        var message by remember { mutableStateOf("") }
        var icon by remember { mutableStateOf(Icons.Default.Check) }
        var action by remember { mutableStateOf("") }
         val stripePaymentViewModel by lazy { StripePaymentViewModel(
            StripePaymentUseCase(
                StripePaymentRepository()
            )
        ) }
        var cardNumber by remember { mutableStateOf("") }
        var month by remember { mutableStateOf("") }
        var year by remember { mutableStateOf("") }
        var cvv by remember { mutableStateOf("") }
        val cardIssuer = detectCardIssuer(cardNumber)

        val clickCreatePayment ={
            val stripePayment = StripePayment(cardNumber, month, year,cvv, "40")
            stripePaymentViewModel.createStripePayment(stripePayment)
        }


        scope.launch {
            stripePaymentViewModel.paymentResult.collect{result->
              if (result?.status == "request_error"){
                  sheetState.show()
                  title = "Error!"
                  message = result.message
                  icon = Icons.Default.Close
              }
            }
        }
        scope.launch {
            stripePaymentViewModel.errorMessage.collect{error->
                if (error.isNotEmpty()){
                    sheetState.show()
                    title = "Error!"
                    message = error
                    icon = Icons.Default.Close
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
                        title = { Text("Payment Methode") },
                        contentColor = Color.White,
                        navigationIcon = {
                            IconButton(onClick = { navigator.pop() }) {
                                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Regresar")
                            }
                        },
                        backgroundColor = Color(Constants.PRIMARY_COLOR),
                        elevation = 0.dp
                    )
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    Card( modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 24.dp, end = 24.dp, bottom = 60.dp)
                        .clip(RoundedCornerShape(8.dp)),
                        elevation = 8.dp)
                    {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(shape = RoundedCornerShape(8.dp))
                                ,
                                contentAlignment = Alignment.Center // Centra el contenido dentro del Box
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally, // Centra el contenido dentro del Column
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.background(Color(Constants.PRIMARY_COLOR))
                                        .fillMaxWidth()
                                ) {
                                    Text("Credit or debit card", color = Color.White)
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(15.dp),
                                        modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
                                    ) {
                                        Image(painter = painterResource("visa.xml"), contentDescription = null, modifier = Modifier.size(24.dp))
                                        Image(painter = painterResource("mastercard.xml"), contentDescription = null, modifier = Modifier.size(24.dp))
                                        Image(painter = painterResource("americanexpress.xml"), contentDescription = null, modifier = Modifier.size(24.dp))
                                    }
                                }
                            }
                            OutlinedTextField(
                                value = cardNumber,
                                onValueChange = {
                                    if (it.length <= 16) {
                                        cardNumber = it
                                    } },
                                label = { Text("Card Number") },
                                visualTransformation = CardNumberVisualTransformation(),
                                trailingIcon = {
                                    when (cardIssuer) {
                                        "Visa" -> Image(painterResource("visa.xml"), contentDescription = "Visa", modifier = Modifier.size(24.dp))
                                        "MasterCard" -> Image(painterResource("mastercard.xml"), contentDescription = "MasterCard",modifier = Modifier.size(24.dp))
                                        "American Express" -> Image(painterResource("americanexpress.xml"), contentDescription = "American Express",modifier = Modifier.size(24.dp))
                                        "Maestro" -> Image(painterResource("maestro.png"), contentDescription = "Maestro",modifier = Modifier.size(24.dp))
                                        else -> {}
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            ExpiryDateField(
                                onMonthChanged = {month = it},
                                onYearChanged = {year = it}
                            )
                            CVVField(
                                cvv = cvv,
                                onCvvChanged = {
                                    cvv = it },
                                cardIssuer = cardIssuer
                            )
                            Button(onClick = {clickCreatePayment()},
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color(Constants.PRIMARY_COLOR)),
                                shape = RoundedCornerShape(20),
                                modifier = Modifier.fillMaxWidth()
                                    .padding(top = 15.dp)
                                    .height(50.dp),) {
                                Text(
                                    text = "Continuar",
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