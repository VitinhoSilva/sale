package com.jvprojetos17.sale.request

data class LoginRequest(
    val cpf: String,
    val password: String
)