package data.repositories.getCostProductProvider

import data.mappers.CostProductProviderDataMapper
import data.models.CostProductProviderDataModel
import data.network.KtorInstance
import domain.entities.CostProductProvider
import domain.interfaces.ICostProductProviderRepository
import domain.resultData.ResultData
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import utils.Constants

class GetCostProductProviderRepository:ICostProductProviderRepository {
    private val client = KtorInstance.client
    override suspend fun getCostProductProvider(idToken: String): ResultData<Map<String, CostProductProvider>> {
        return try {
            val response: HttpResponse = client.get("${Constants.FIREBASE_REALTIME_API}IdProductCountryInnoverit.json?auth=$idToken")
            val costProductProviderMap = response.body<Map<String, CostProductProviderDataModel>>()
            val domainMap = costProductProviderMap.mapValues {entry->
                CostProductProviderDataMapper.mapToDomain(entry.value)
            }
            ResultData.Success(domainMap)
        }catch (e:Exception){
            ResultData.Error(e)
        }
    }
}