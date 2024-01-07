package domain.useCase.getCountry

import domain.entities.Country
import domain.interfaces.ICountriesRepository
import domain.resultData.ResultData

class GetCountriesUseCase(private val repository: ICountriesRepository) {
    suspend operator fun invoke(): ResultData<List<Country>> =
        repository.getCountries()
}