package data.repositories.getCountry

import data.mappers.CountryDataMapper
import data.models.CountryDataModel
import data.network.KtorInstance
import domain.entities.Country
import domain.interfaces.ICountriesRepository
import domain.resultData.ResultData
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import utils.Constants

class GetCountriesRepository:ICountriesRepository {
    private val client = KtorInstance.client
    override suspend fun getCountries(): ResultData<List<Country>> {
        return try {
            val response: HttpResponse =  client.get("${Constants.FIREBASE_REALTIME_API}countries.json")
            val responseBody = response.bodyAsText()
            val countriesDataModel = Json.decodeFromString<List<CountryDataModel>>(responseBody)
            val countries = countriesDataModel.map(CountryDataMapper::mapToDomain)
            ResultData.Success(countries)
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }
}