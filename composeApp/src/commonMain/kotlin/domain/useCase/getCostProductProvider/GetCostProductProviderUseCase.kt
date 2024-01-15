package domain.useCase.getCostProductProvider

import data.repositories.getCostProductProvider.GetCostProductProviderRepository
import domain.entities.CostProductProvider
import domain.resultData.ResultData

class GetCostProductProviderUseCase(private val getCostProductProductProviderRepository: GetCostProductProviderRepository) {
    suspend operator fun invoke(idToken:String) :ResultData<Map<String,CostProductProvider>> =
        getCostProductProductProviderRepository.getCostProductProvider(idToken)
}