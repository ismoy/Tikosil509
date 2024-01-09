package ui.profile

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBackIos
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.repositories.updateProfile.UpdateProfileRepository
import domain.entities.Users
import domain.resultData.ResultData
import domain.useCase.updateProfile.UpdateProfileUseCase
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import presentation.updateProfile.UpdateProfileViewModel
import ui.home.HomeScreen
import utils.Constants
import utils.DataRepository
import utils.DataUserShared
import utils.GlobalBottomSheet

class ProfileScreen:Screen {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()
        val navigator = LocalNavigator.currentOrThrow
        val usersData by remember { mutableStateOf( DataUserShared.getSharedUsersData()) }
        val data by remember { mutableStateOf( DataRepository.getSharedData()) }
        var firstName by remember { mutableStateOf(usersData?.data?.firstname) }
        var lastName by remember { mutableStateOf(usersData?.data?.lastname) }
        var password by remember { mutableStateOf(usersData?.data?.password) }
        var phone by remember { mutableStateOf(usersData?.data?.phone) }
        val imageUrl = usersData?.data?.image ?: "https://img.freepik.com/vector-gratis/vector-degradado-logotipo-colorido-pajaro_343694-1365.jpg"
        val updateProfileViewModel by lazy { UpdateProfileViewModel(UpdateProfileUseCase(
            UpdateProfileRepository()
        )) }
        var title by remember { mutableStateOf("") }
        var message by remember { mutableStateOf("") }
        var icon by remember { mutableStateOf(Icons.Default.Check) }
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

        val updateProfile = {
            val users = Users(usersData?.data?.id,usersData?.data?.countryselected.toString(),
                usersData?.data?.countrycode.toString(),firstName.toString(),lastName.toString(),usersData?.data?.email.toString(),phone.toString(),usersData?.data?.role!!.toInt(),
                password.toString(),password.toString(),usersData?.data?.soldmoncash!!.toFloat(),usersData?.data?.soltopup!!.toFloat(),
                usersData?.data?.soldnatcash!!.toFloat(),usersData?.data?.soldlapoula!!.toFloat(),usersData?.data?.status!!.toInt(),
                usersData?.data?.image.toString() )
            updateProfileViewModel.updateProfile(data?.data?.userId.toString(),data?.data?.idToken.toString(),users)
            coroutineScope.launch {
              updateProfileViewModel.updateProfileResponse.collect{userResponse->
                  if (userResponse!=null){
                      sheetState.show()
                      title = "Success"
                      message = "Su cuenta ha sido actualizado con éxito"
                      icon = Icons.Default.Check
                  }
              }
            }
        }
        coroutineScope.launch {
            updateProfileViewModel.errorProfileResponse.collect{errorUpdate->
                if (errorUpdate.isNotEmpty()){
                    sheetState.show()
                    title = "Error"
                    message = errorUpdate
                    icon = Icons.Default.Close
                }
            }
        }

        GlobalBottomSheet(
            title = title,
            sheetState = sheetState,
            message = message,
            icon = icon,
            action = "",
            onDismiss = {coroutineScope.launch {
                sheetState.hide()
                navigator.push(HomeScreen())
            }}
        ){
            Scaffold (topBar = {
                TopAppBar(
                    title = {},
                    contentColor = Color.White,
                    navigationIcon = {
                        IconButton(onClick = {
                           navigator.pop()
                        }) {
                            Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "ArrowBack")
                        }
                    },
                    backgroundColor = Color(Constants.PRIMARY_COLOR),
                    elevation = 0.dp
                )
            }, scaffoldState = scaffoldState){
                MaterialTheme{
                    Column(modifier = Modifier.fillMaxWidth()
                        .background(Color(Constants.PRIMARY_COLOR))) {
                        Column(
                            modifier = Modifier
                                .padding(start = 24.dp, end = 24.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .offset(y = 8.dp)
                            ) {
                                KamelImage(
                                    resource = asyncPainterResource(data = imageUrl),
                                    contentDescription = "Profile",
                                    animationSpec = tween(),
                                    modifier = Modifier
                                        .size(70.dp)
                                        .clip(CircleShape)
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.White)
                                    .height(60.dp)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                when (usersData?.data?.role) {
                                    1 -> {
                                        Text("Agent", color = Color.Black)
                                    }
                                    2 -> {
                                        Text("Master", color = Color.Black)
                                    }
                                    else -> {
                                        Text("Administrador", color = Color.Black)
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(top = 30.dp))

                        Column(
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(horizontal = 24.dp)
                        ) {
                            Text("FirstName", modifier = Modifier.padding(top = 10.dp))
                            Box {
                                OutlinedTextField(
                                    value = "$firstName",
                                    onValueChange = {firstName =it  },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = Color.Black,
                                        focusedIndicatorColor = Color(Constants.PRIMARY_COLOR),
                                        backgroundColor = Color.Transparent
                                    )
                                )
                            }
                            Text("LastName", modifier = Modifier.padding(top = 10.dp))
                            Box {
                                OutlinedTextField(
                                    value = "$lastName",
                                    onValueChange = { lastName = it },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = Color.Black,
                                        focusedIndicatorColor = Color(Constants.PRIMARY_COLOR),
                                        backgroundColor = Color.Transparent
                                    )
                                )
                            }
                            Text("Email", modifier = Modifier.padding(top = 10.dp))
                            Box {
                                OutlinedTextField(
                                    value = "${usersData?.data?.email}",
                                    readOnly = true,
                                    onValueChange = { },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = Color.Black,
                                        focusedIndicatorColor = Color(Constants.PRIMARY_COLOR),
                                        backgroundColor = Color.Transparent
                                    )
                                )
                            }
                            Text("Password", modifier = Modifier.padding(top = 10.dp))
                            Box {
                                OutlinedTextField(
                                    value = "$password",
                                    onValueChange = {password = it },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = Color.Black,
                                        focusedIndicatorColor = Color(Constants.PRIMARY_COLOR),
                                        backgroundColor = Color.Transparent
                                    )
                                )
                            }
                            Text("Phone", modifier = Modifier.padding(top = 10.dp))
                            Box {
                                OutlinedTextField(
                                    value = "$phone",
                                    onValueChange = {phone = it },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    colors = TextFieldDefaults.textFieldColors(
                                        textColor = Color.Black,
                                        focusedIndicatorColor = Color(Constants.PRIMARY_COLOR),
                                        backgroundColor = Color.Transparent
                                    )
                                )
                            }

                            Box(modifier = Modifier.fillMaxWidth()
                                .padding(top = 15.dp, bottom = 10.dp)) {
                                Button(onClick = {
                                    updateProfile()
                                },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(
                                        Constants.PRIMARY_COLOR
                                    )),
                                    shape = RoundedCornerShape(20),
                                    modifier = Modifier.fillMaxWidth()
                                        .height(50.dp),) {
                                    Text(
                                        text = "Actualizar perfil",
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
}