package data.dataSource

import androidx.compose.runtime.mutableStateOf
import data.repositories.sendRecharge.SendTopUpViaProviderRepository
import data.repositories.updateSoldTopUpUser.UpdateSoldTopUpUserRepository
import domain.entities.Product
import domain.entities.SharedProductData
import domain.entities.Users
import domain.useCase.sendRecharge.SendTopUpViaProviderUseCase
import domain.useCase.updateSoldTopUpUser.UpdateSoldTopUpUserUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import presentation.sendRecharge.SendTopUpViaProviderViewModel
import presentation.updateSoldTopUpUser.UpdateSoldTopUpUserViewModel
import utils.Constants.TOPUP
import utils.DataProductShared
import utils.DataRepository
import utils.DataUserShared

class SendTopUpRecharge{
    val data =  mutableStateOf( DataRepository.getSharedData())
    val userInfoData = mutableStateOf(DataUserShared.getSharedUsersData())
    private val scope = CoroutineScope(Dispatchers.Main)
    private var _isNotEnoughBalanceTopUp = MutableSharedFlow<Boolean>()
    val isNotEnoughBalanceTopUp = _isNotEnoughBalanceTopUp.asSharedFlow()
    private val sendTopUpViaProviderViewModel by lazy { SendTopUpViaProviderViewModel(
        SendTopUpViaProviderUseCase(SendTopUpViaProviderRepository())
    ) }
    private var _isTopUpSend = MutableStateFlow("")
    val isTopUpSend = _isTopUpSend.asStateFlow()
    private val updateSoldTopUpUserViewModel by lazy { UpdateSoldTopUpUserViewModel(
        UpdateSoldTopUpUserUseCase(UpdateSoldTopUpUserRepository())
    ) }
    private var _isTopUpNotSend = MutableStateFlow("")
    val isTopUpNotSend = _isTopUpNotSend.asStateFlow()
    private var _isPaymentTopUpAgent = MutableStateFlow<Boolean?>(null)
    val isPaymentTopUpAgent = _isPaymentTopUpAgent.asSharedFlow()



    fun startPayment(topUpSelected:Float, operatorSelected:String,phone:String,subTotalSelected:String,
                 idProductSelected:Long,countrySelected:String){
        when (userInfoData.value?.data?.role) {
            2 -> {verifyAccountBeforeSendTopUp(topUpSelected,phone,idProductSelected)}
            1 -> {goToPayWithMercadoPago(topUpSelected,
                operatorSelected,phone,subTotalSelected,idProductSelected,countrySelected)}
            else->{}
        }
    }

    private fun goToPayWithMercadoPago(
        topUpSelected: Float,
        operatorSelected: String,
        phone: String,
        subTotalSelected: String,
        idProductSelected: Long,
        countrySelected: String
    ) {
        val currentDateTime = getCurrentDateTimeFormatted()

        userInfoData.value?.data.also {users->
            val product = Product(TOPUP,topUpSelected,users?.email.toString(),
                operatorSelected,phone,subTotalSelected,idProductSelected,
                users?.id.toString(),users?.firstname.toString(),users?.lastname.toString(),users?.role?:1,"","",
                currentDateTime,0,countrySelected,users?.image.toString(),
                topUpSelected,subTotalSelected,"")
            DataProductShared.setSharedProductData(SharedProductData(product))
          scope.launch {
              _isPaymentTopUpAgent.emit(true)
          }
        }
    }

    private fun verifyAccountBeforeSendTopUp(
        topUpSelected: Float,
        phone: String,
        idProductSelected: Long
    ) {
        userInfoData.value?.data.also {users->
            if (topUpSelected > users?.soltopup!!){
                scope.launch {
                    _isNotEnoughBalanceTopUp.emit(true)
                }
            }else{
                createProcessToPay(topUpSelected,phone,idProductSelected,users)
            }
        }
    }

    private fun createProcessToPay(
        topUpSelected: Float,
        phone: String,
        idProductSelected: Long,
        users: Users
    ) {
        sendTopUpViaProviderViewModel.sendTopUpViaProvider(idProductSelected.toInt(),phone)
        scope.launch {
            sendTopUpViaProviderViewModel.sendTopUpViaProviderResponse.collect{result->
                if (result?.status == "success"){
                    val newSolTopUp = users.soltopup?.minus(topUpSelected)
                    _isTopUpSend.emit(result.status)
                    if (newSolTopUp != null) {
                        updateSoldTopUpUserViewModel.updateSoldTopUpUser(data.value?.data?.userId.toString(),data.value?.data?.idToken.toString(),newSolTopUp)
                    }
                }

            }
        }
        scope.launch {
            sendTopUpViaProviderViewModel.errorSendTopUpViaProvider.collect{
                if (it.isNotEmpty()){
                    _isTopUpNotSend.emit(it)
                }
            }
        }
    }

    private fun getCurrentDateTimeFormatted(): String {
        val currentMoment = Clock.System.now()
        val dateTime = currentMoment.toLocalDateTime(TimeZone.currentSystemDefault())
        return "${dateTime.dayOfMonth}/${dateTime.monthNumber}/${dateTime.year} ${dateTime.hour}:${dateTime.minute}:${dateTime.second}"
    }
}