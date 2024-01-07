package domain.resultData

sealed class ResultData <out T>{
    data class Success<T>(val data:T):ResultData<T>()
    data class Error(val exception: Throwable):ResultData<Nothing>()
}