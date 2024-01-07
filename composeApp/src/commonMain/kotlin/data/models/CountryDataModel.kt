package data.models

import kotlinx.serialization.Serializable


@Serializable
data class CountryDataModel(
    var name: String? = null,
    var topLevelDomain: ArrayList<String>? = null,
    var alpha2Code: String? = null,
    var alpha3Code: String? = null,
    var callingCodes: ArrayList<String>? = null,
    var capital: String? = null,
    var altSpellings: ArrayList<String>? = null,
    var subregion: String? = null,
    var region: String? = null,
    var population:Int?= 0,
    var latlng: ArrayList<Double>? = null,
    var demonym: String? = null,
    var area:Double?= 0.0,
    var timezones:ArrayList<String>? = null,
    var borders: ArrayList<String>? = null,
    var nativeName: String? = null,
    var numericCode: String? = null,
    var flags: FlagsDataModel? = null,
    var currencies: ArrayList<CurrencyDataModel>? = null,
    var languages: ArrayList<LanguageDataModel>? = null,
    var translations: TranslationsDataModel? = null,
    var flag: String? = null,
    var regionalBlocs: ArrayList<RegionalBlocDataModel>? = null,
    var cioc: String? = null,
    var independent:Boolean?= false,
    var gini:Double?= 0.0
)