package data.mappers

import data.models.CostProductProviderDataModel
import domain.entities.CostProductProvider

object CostProductProviderDataMapper {
    fun mapToDomain(model:CostProductProviderDataModel):CostProductProvider{
        return CostProductProvider(
            priceReceiver = model.priceReceiver,
            operatorName = model.operatorName,
            priceSales = model.priceSales,
            nameMoneyCountryReceiver = model.nameMoneyCountryReceiver,
            nameMoneyCountrySale = model.nameMoneyCountrySale,
            idProduct = model.idProduct,
            country = model.country,
            formatPrice = model.formatPrice,
            idKey = model.idKey
        )
    }
}