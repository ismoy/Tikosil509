package data.repositories.getUsers

import data.mappers.UsersDataMapper
import data.models.UsersDataModel
import data.network.KtorInstance
import domain.entities.Users
import domain.interfaces.IGetUsersRepository
import domain.resultData.ResultData
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import utils.Constants

class GetUserRepository:IGetUsersRepository {
    private val client = KtorInstance.client
    override suspend fun getUser(userId: String, idToken: String): ResultData<Users> {
        return try {
            val response: HttpResponse = client.get("${Constants.FIREBASE_REALTIME_API}Users/$userId.json?auth=$idToken")
            val userDataModel = response.body<UsersDataModel>()
            ResultData.Success(UsersDataMapper.mapToDomain(userDataModel))
        } catch (e: Exception) {
            ResultData.Error(e)
        }
    }
}