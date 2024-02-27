package com.hahow.android_recruit_project.apiservice

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()

    object Complete : ApiResult<Nothing>()

}
