package domain.interfaces

import domain.entities.Country
import domain.resultData.ResultData

interface ICountriesRepository {
    suspend fun getCountries():ResultData<List<Country>>
}