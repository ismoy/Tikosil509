package domain.useCase.sendSales

import data.repositories.sendSales.SendSalesRepository
import domain.entities.Sales
import domain.resultData.ResultData

class SendSalesUseCase(private val sendSalesRepository: SendSalesRepository) {
    suspend operator fun invoke(sales: Sales,authToken:String,userIdTypeSelected:String):ResultData<Sales> =
        sendSalesRepository.sendSales(sales, authToken, userIdTypeSelected)
}