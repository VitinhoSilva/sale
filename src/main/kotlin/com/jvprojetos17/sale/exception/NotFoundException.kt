package com.jvprojetos17.sale.exception

class NotFoundException(override val message: String, val errorCode: String) : Exception()
