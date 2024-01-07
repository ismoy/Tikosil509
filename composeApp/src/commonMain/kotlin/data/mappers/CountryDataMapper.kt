package data.mappers

import data.models.CountryDataModel
import domain.entities.Country
import domain.entities.Currency
import domain.entities.Flags
import domain.entities.Language
import domain.entities.RegionalBloc
import domain.entities.Translations

object CountryDataMapper {
    fun mapToDomain(model: CountryDataModel): Country {
        return Country(
            name = model.name,
            capital = model.capital,
            topLevelDomain = model.topLevelDomain,
            alpha2Code = model.alpha2Code,
            alpha3Code = model.alpha3Code,
            callingCodes = model.callingCodes,
            altSpellings = model.altSpellings,
            subregion = model.subregion,
            region = model.region,
            population = model.population,
            latlng = model.latlng,
            demonym = model.demonym,
            area = model.area,
            timezones = model.timezones,
            borders = model.borders,
            nativeName = model.nativeName,
            numericCode = model.numericCode,
            flag = model.flag,
            flags = model.flags?.let { flagsDataModel ->
                Flags(
                    png = flagsDataModel.png,
                    svg = flagsDataModel.svg
                )
            },
            currencies = model.currencies?.map { currencyDataModel ->
                Currency(
                    code = currencyDataModel.code,
                    name = currencyDataModel.name,
                    symbol = currencyDataModel.symbol
                )
            }?.let { ArrayList(it) },
            languages = model.languages?.map {languageDataModels ->
                       Language(
                           iso6391 = languageDataModels.iso6391,
                           iso6392 = languageDataModels.iso6392,
                           name = languageDataModels.name,
                           nativeName = languageDataModels.nativeName
                       )
            }?.let { ArrayList(it) },
            translations = model.translations?.let {translationsDataModel ->
                          Translations(
                              br = translationsDataModel.br,
                              de = translationsDataModel.de,
                              es = translationsDataModel.es,
                              fa = translationsDataModel.fa,
                              fr = translationsDataModel.fr,
                              hr = translationsDataModel.hr,
                              hu = translationsDataModel.hu,
                              it = translationsDataModel.it,
                              ja = translationsDataModel.ja,
                              nl = translationsDataModel.nl,
                              pt = translationsDataModel.pt
                          )
            },
            regionalBlocs = model.regionalBlocs?.map { regionalBlocDataModel ->
                RegionalBloc(
                    acronym = regionalBlocDataModel.acronym,
                    name = regionalBlocDataModel.name,
                    otherNames = regionalBlocDataModel.otherNames?.toList() ?: emptyList(),
                    otherAcronyms = regionalBlocDataModel.otherAcronyms?.toList() ?: emptyList()
                )
            }?.let { ArrayList(it) },

            cioc = model.cioc,
            independent = model.independent,
            gini = model.gini)
    }
}