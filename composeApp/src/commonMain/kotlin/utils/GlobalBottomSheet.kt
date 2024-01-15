package utils

import BaseViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.home.HomeScreen
import utils.Constants.LAPOULA
import utils.Constants.MONCASH
import utils.Constants.NATCASH
import utils.Constants.REGISTER_USER
import utils.Constants.TOPUP

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GlobalBottomSheet(
    sheetState: ModalBottomSheetState,
    title: String,
    message: String,
    icon: ImageVector,
    action:String,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val navigator = LocalNavigator.currentOrThrow
   // val whatsAppLinkOpener by remember { mutableStateOf(WhatsAppLinkOpener()) }
    /*val sendWhatsapp = {
        whatsAppLinkOpener.openWhatsApp(
            Constants.PHONE_NUMBER_WHATSAPP,
            "Hola estoy probando"
        )
    }*/
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(MaterialTheme.colors.surface),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Icon(icon, contentDescription = null, modifier = Modifier.padding(top = 10.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = message, style = MaterialTheme.typography.body2,
                        modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 5.dp)
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp, start = 24.dp, end = 24.dp)
                    ) {
                        Button(
                            onClick = {
                                /*if (action == MONCASH || action == NATCASH || action == LAPOULA || action == TOPUP) {
                                    navigator.push(HomeScreen())
                                    sendWhatsapp()
                                }*/

                                    navigator.pop()
                                    BaseViewModel().clearViewModel()
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(Constants.PRIMARY_COLOR)),
                            shape = RoundedCornerShape(20),
                            modifier = Modifier.fillMaxWidth()
                                .height(50.dp),
                        ) {
                            Text(
                                text = "Entiendo",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
        },
        modifier = Modifier.fillMaxWidth(),
        sheetGesturesEnabled = false,
        sheetElevation = 30.dp
    ) {
        content()
    }
}



