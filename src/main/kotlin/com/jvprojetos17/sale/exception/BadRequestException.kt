package com.jvprojetos17.sale.exception

class BadRequestException(override val message: String, val errorCode: String) : Exception() {
}