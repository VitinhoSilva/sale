package com.jvprojetos17.sale.request

import javax.validation.constraints.NotEmpty

data class UserRequest(
    var id: Long,

    @field:NotEmpty(message = "Nome deve ser informado!")
    var name: String,

    @field:NotEmpty(message = "Cpf deve ser informado!")
    var cpf: String,

    @field:NotEmpty(message = "Email deve ser informado!")
    var email: String,
)
