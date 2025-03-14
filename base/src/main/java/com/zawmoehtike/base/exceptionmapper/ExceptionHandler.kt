package com.zawmoehtike.base.exceptionmapper

import com.zawmoehtike.share.mappers.UnidirectionalSuspendMap


interface ExceptionHandler : UnidirectionalSuspendMap<Throwable, String> {
    fun getCode(item: Throwable): Int
    suspend fun getErrorBody(item: Throwable): String?
}
