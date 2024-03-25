package ui.home

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.repositories.getSales.GetSalesRepository
import data.repositories.getUsers.GetUserRepository
import domain.entities.Sales
import domain.entities.SharedUsersData
import domain.entities.Users
import domain.useCase.getSales.GetSalesUseCase
import domain.useCase.getUser.GetUserUseCase
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import presentation.getSales.GetSalesViewModel
import presentation.getUser.GetUserViewModel
import ui.history.HistoryScreen
import ui.profile.ProfileScreen
import ui.sendRecharge.SendRechargeScreen
import utils.Constants
import utils.DataRepository
import utils.DataUserShared
import utils.GlobalBottomSheet
import utils.roundTo2DecimalPlaces

class HomeScreen:Screen {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val data by remember { mutableStateOf( DataRepository.getSharedData()) }
        val startColor = Color(Constants.PRIMARY_COLOR)
        val centerColor = Color(Constants.CENTRAL_COLOR)
        val endColor = Color(Constants.PRIMARY_COLOR)
        val gradientBrush = Brush.horizontalGradient(
            colors = listOf(startColor, centerColor, endColor))
        val coroutineScope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()
        val titleDrawer = listOf("Send recharge","Shopping history","Technical support","Profile")
        val navigator = LocalNavigator.currentOrThrow
        var userResult by remember { mutableStateOf(Users()) }
        val imageUrl = userResult.image ?: "https://img.freepik.com/vector-gratis/vector-degradado-logotipo-colorido-pajaro_343694-1365.jpg"
        val usersViewModel = GetUserViewModel(GetUserUseCase(GetUserRepository()))
        var totalBalanceMaster by remember { mutableStateOf(0F) }
        var title by remember { mutableStateOf("") }
        var message by remember { mutableStateOf("") }
        var icon by remember { mutableStateOf(Icons.Default.Check) }
        val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        var lastSales by remember { mutableStateOf<Map<String, Sales>?>(null) }
        val salesViewModel by lazy { GetSalesViewModel(GetSalesUseCase(GetSalesRepository())) }

        LaunchedEffect(key){
            usersViewModel.getUsers(data?.data?.userId.toString(),data?.data?.idToken.toString())
            usersViewModel.users.collect{
                if (it !=null){
                    userResult = it
                    DataUserShared.setSharedUsersData(SharedUsersData(it))
                    totalBalanceMaster =
                        it.soldmoncash?.let { it1 -> it.soldnatcash?.let { it2 ->
                            it.soltopup?.let { it3 ->
                                it.soldlapoula?.plus(it1)?.plus(
                                    it2
                                )?.plus(it3)
                            }
                        } }!!
                }
            }
        }
        coroutineScope.launch {
            usersViewModel.errorUserResponse.collect{
               if (it.isNotEmpty()){
                   title = "Error!"
                   message = it
                   icon = Icons.Default.Close
                   sheetState.show()
               }
            }
        }
        LaunchedEffect(key1 = null){
            salesViewModel.getSales(data?.data?.idToken.toString())
            salesViewModel.getSalesResponse.collect{
                lastSales = it
            }
        }
        coroutineScope.launch {
            salesViewModel.errorGetSalesResponse.collect{errorSales->
                if (errorSales.isNotEmpty()){
                    title = "Error!"
                    message = errorSales
                    icon = Icons.Default.Close
                    sheetState.show()
                }
            }
        }
        GlobalBottomSheet(
            title = title,
            sheetState = sheetState,
            message = message,
            icon = icon,
            action = "",
            onDismiss = {coroutineScope.launch { sheetState.hide() }}
        ){
            Scaffold (topBar = {
                TopAppBar(
                    title = { Text("") },
                    contentColor = Color.White,
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }) {
                            Icon(imageVector = Icons.Rounded.Menu, contentDescription = "Menu")
                        }
                    },
                    backgroundColor = Color(Constants.PRIMARY_COLOR),
                    elevation = 0.dp
                )
            },
                scaffoldState = scaffoldState,
                drawerContent = {
                    Column(modifier = Modifier.fillMaxWidth()
                        .background(Color(Constants.PRIMARY_COLOR))
                        .padding(top = 40.dp)) {
                        Box(modifier = Modifier
                            .padding(start = 10.dp)) {
                            KamelImage(
                                resource = asyncPainterResource(data = imageUrl),
                                contentDescription = "Profile",
                                animationSpec = tween(),
                                modifier = Modifier
                                    .size(70.dp)
                                    .clip(CircleShape)
                            )
                        }
                        Box(modifier = Modifier.fillMaxWidth()
                            .padding(top = 15.dp, start = 15.dp)) {
                            Text("Hola, ${userResult.firstname}", color = Color.White, fontSize = 18.sp)
                        }
                        Box(modifier = Modifier.fillMaxWidth()
                            .padding(top = 1.dp, bottom = 10.dp, start = 15.dp)) {
                            Text("${userResult.email}", color = Color.White, fontSize = 18.sp)
                        }

                    }
                    LazyColumn(
                        modifier = Modifier.padding(top = 30.dp)
                    ) {
                        items(titleDrawer.size) { index ->
                            Box(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(Constants.COLOR_HISTORY))
                                    .height(60.dp)
                                    .clickable {
                                        when(index){
                                            0->navigator.push(SendRechargeScreen())
                                            1->navigator.push(HistoryScreen())
                                            2->{}
                                            3->navigator.push(ProfileScreen())
                                        }
                                    }
                                ,
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 8.dp, end = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    KamelImage(
                                        resource = asyncPainterResource(data = "https://img.freepik.com/vector-gratis/vector-degradado-logotipo-colorido-pajaro_343694-1365.jpg"),
                                        contentDescription = "Profile",
                                        animationSpec = tween(),
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clip(CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(titleDrawer[index], fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            ){
                MaterialTheme{
                    Column(modifier = Modifier.fillMaxWidth()
                        .background(Color(Constants.PRIMARY_COLOR))) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, bottom = 20.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(0.80f)
                                    .align(Alignment.CenterVertically)
                            ) {
                                Text("Bienvenid@: ${userResult.firstname}", color = Color.White)
                            }

                            Box(
                                modifier = Modifier
                                    .weight(0.20f)
                                    .padding(end = 8.dp)
                                    .align(Alignment.CenterVertically)
                            ) {
                                KamelImage(
                                    resource = asyncPainterResource(data = imageUrl),
                                    contentDescription = "Profile",
                                    animationSpec = tween(),
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                )
                            }
                        }

                        if (userResult.role ==2){
                            Column(modifier = Modifier
                                .fillMaxWidth()
                                .background(brush = gradientBrush)
                                .align(Alignment.CenterHorizontally)) {
                                Box(
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                ) {
                                    Text("Balance Total", color = Color.White)
                                }
                                Box(
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                ) {
                                    Text(totalBalanceMaster.toDouble().roundTo2DecimalPlaces().toString())
                                }
                            }
                            Spacer(modifier = Modifier.padding(top = 60.dp))
                        }
                        Spacer(modifier = Modifier.padding(top = 30.dp))

                        Box(
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 50.dp,
                                        topEnd = 50.dp
                                    )
                                )
                                .background(Color.White)
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(top = 20.dp, start = 8.dp, end = 8.dp)
                        ) {
                            when(userResult.role){
                                2->{
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 15.dp, end = 15.dp)
                                            .background(brush = gradientBrush),
                                        Alignment.Center

                                    ) {
                                        Text("Last 10 pucharses", color = Color.White)
                                    }
                                }
                                3->{}
                                1->{
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 15.dp, end = 15.dp)
                                            .background(brush = gradientBrush),
                                        Alignment.Center

                                    ) {
                                        Text("Last 10 pucharses", color = Color.White)
                                    }
                                }
                            }
                            LazyColumn(
                                modifier = Modifier.padding(top = 30.dp)
                            ) {
                                lastSales?.values?.let { salesList ->
                                    val filteredSalesList = salesList.filter { sale -> sale.idUser == userResult.id }
                                    val startIndex = if (filteredSalesList.size > 10) filteredSalesList.size - 10 else 0
                                    val endIndex = filteredSalesList.size
                                    items( filteredSalesList.size ) { index ->
                                        if (index in startIndex until endIndex){
                                            val sale = filteredSalesList.elementAt(index)
                                            Box(
                                                modifier = Modifier
                                                    .padding(8.dp)
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(Color(Constants.COLOR_HISTORY))
                                                    .height(60.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(start = 8.dp, end = 8.dp),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    KamelImage(
                                                        resource = asyncPainterResource(data = "${sale.image}"),
                                                        contentDescription = "Profile",
                                                        animationSpec = tween(),
                                                        modifier = Modifier
                                                            .size(30.dp)
                                                            .clip(RectangleShape)
                                                    )
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Text(sale.typeRecharge ?: "", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Text("${sale.currency}${sale.subtotal}", fontSize = 14.sp)
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Text(sale.date ?: "", fontSize = 14.sp)
                                                }
                                            }
                                        }

                                    }
                                }
                            }


                        }
                    }

                }
            }
        }

    }
}