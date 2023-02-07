package com.jvprojetos17.sale.exception

class BusinessException(override val message: String, val errorCode: String) : Exception()
