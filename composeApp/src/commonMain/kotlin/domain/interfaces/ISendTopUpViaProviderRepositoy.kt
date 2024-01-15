package domain.interfaces

import domain.entities.RechargeSuccessResponse
import domain.resultData.ResultData

interface ISendTopUpViaProviderRepositoy {
    suspend fun sendTopUpViaProvider(idProduct:Int,destination:String):ResultData<RechargeSuccessResponse>
}