package com.example.spotifyapp.domain.model.local

sealed class NetworkResponse<out T>(val data : T?,val message : String?, val status : ResponseStatus?){
    data class Success<T>(val _data : T) : NetworkResponse<T>(
        data = _data,
        message = null,
        status = ResponseStatus.SUCCESS
    )
    data class Error(val _message : String) : NetworkResponse<Nothing>(
        data = null,
        message = _message,
        status = ResponseStatus.ERROR
    )
    data object Loading : NetworkResponse<Nothing>(
        data = null,
        message = null,
        status = ResponseStatus.LOADING
    )
}


enum class ResponseStatus{
    SUCCESS,
    LOADING,
    ERROR
}