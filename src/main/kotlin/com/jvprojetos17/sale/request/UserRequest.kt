package com.jvprojetos17.sale.request

import javax.validation.constraints.NotEmpty

data class UserRequest(

    @field:NotEmpty(message = "Description must be informed!")
    var name: String,

    @field:NotEmpty(message = "Cpf must be informed!")
    var cpf: String,

    @field:NotEmpty(message = "Email must be informed!")
    var email: String,
)
