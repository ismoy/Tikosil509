package domain.entities

import kotlinx.serialization.Serializable

@Serializable
data class Country (
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
    var flags: Flags? = null,
    var currencies: ArrayList<Currency>? = null,
    var languages: ArrayList<Language>? = null,
    var translations: Translations? = null,
    var flag: String? = null,
    var regionalBlocs: ArrayList<RegionalBloc>? = null,
    var cioc: String? = null,
    var independent:Boolean?= false,
    var gini:Double?= 0.0
)
