package domain.useCase.getSales

import data.repositories.getSales.GetSalesRepository
import domain.entities.Sales
import domain.resultData.ResultData

class GetSalesUseCase(private val getSalesRepository: GetSalesRepository) {
    suspend operator fun invoke(authToken:String):ResultData<Map<String,Sales>> =
        getSalesRepository.getSales(authToken)
}