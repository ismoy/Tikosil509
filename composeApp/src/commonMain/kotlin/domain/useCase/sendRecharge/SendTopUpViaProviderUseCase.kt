package domain.useCase.sendRecharge

import data.repositories.sendRecharge.SendTopUpViaProviderRepository
import domain.entities.RechargeSuccessResponse
import domain.resultData.ResultData

class SendTopUpViaProviderUseCase(private val sendRechargeViaProviderRepository: SendTopUpViaProviderRepository) {
    suspend operator fun invoke(idProduct:Int,destination:String):ResultData<RechargeSuccessResponse> =
        sendRechargeViaProviderRepository.sendTopUpViaProvider(idProduct, destination)
}