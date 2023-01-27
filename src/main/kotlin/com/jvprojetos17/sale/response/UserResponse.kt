package com.jvprojetos17.sale.response

import com.jvprojetos17.sale.enums.Status

data class UserResponse (
    var id: Long?,
    var name: String,
    var cpf: String,
    var email: String,
    var active: Status?
)