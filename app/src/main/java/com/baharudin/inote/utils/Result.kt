package com.baharudin.inote.utils

sealed class Result<T>(val data:T? = null, val messege : String? = null) {
    class Succes<T> (data: T?, messege: String? = null): Result<T>(data, messege)
    class Error<T>(messege: String? , data: T? = null): Result<T>(data, messege)
    class Loading<T>:Result<T>()
}
