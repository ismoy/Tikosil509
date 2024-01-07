package utils

import domain.entities.SharedCountriesData
import domain.entities.SharedData

object DataRepository {
    private var sharedData: SharedData?=null
    fun setSharedData(data: SharedData){
        sharedData = data
    }
    fun getSharedData(): SharedData?{
        return sharedData
    }
}
/*
object DataUserShared{
    private var sharedUsersData: SharedUsersData?=null
    fun setSharedUsersData(data: SharedUsersData){
        sharedUsersData = data
    }
    fun getSharedUsersData(): SharedUsersData?{
        return sharedUsersData
    }
}*/
object DataCountryShared{
    private var sharedCountriesData:SharedCountriesData?=null
    fun setSharedCountriesData(data:SharedCountriesData){
        sharedCountriesData = data
    }
    fun getSharedCountriesData():SharedCountriesData?{
        return sharedCountriesData
    }
}

/*object DataProductShared{
    private var sharedProductData:SharedProductData?=null
    fun setSharedProductData(productData: SharedProductData){
        sharedProductData = productData
    }
    fun getSharedProductData():SharedProductData?{
        return sharedProductData
    }
}*/

/*object DataFirebaseApisShared{
    private var sharedFirebaseApisData:SharedFirebaseApisData?=null
    fun setSharedFirebaseApisData(firebaseApisData: SharedFirebaseApisData){
        sharedFirebaseApisData = firebaseApisData
    }

    fun getSharedFirebaseApisData():SharedFirebaseApisData?=
        sharedFirebaseApisData
}*/