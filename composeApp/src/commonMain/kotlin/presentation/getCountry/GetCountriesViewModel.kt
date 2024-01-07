package presentation.getCountry

import BaseViewModel
import domain.entities.Country
import domain.resultData.ResultData
import domain.useCase.getCountry.GetCountriesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GetCountriesViewModel(private val getCountriesUseCase: GetCountriesUseCase):BaseViewModel() {
    private val _countriesResponse = MutableStateFlow<List<Country>>(emptyList())
    val countriesResponse = _countriesResponse.asStateFlow()
    private val _errorCountriesResponse =MutableStateFlow<String?>("")
    val errorCountriesResponse: StateFlow<String?> = _errorCountriesResponse

    fun getCountries() {
        viewModelScope.launch {
                when (val result = getCountriesUseCase()) {
                    is ResultData.Success ->
                        _countriesResponse.emit(result.data)
                    is ResultData.Error -> {
                        _errorCountriesResponse.emit(result.exception.message)
                        println("Errorcountry ${result.exception.message}")
                    }
                }
        }
    }
}