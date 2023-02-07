package com.jvprojetos17.sale.exception

class AuthenticationException(override val message: String, val errorCode: String) : Exception()
