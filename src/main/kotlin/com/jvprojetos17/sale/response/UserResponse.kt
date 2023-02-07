package com.jvprojetos17.sale.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.jvprojetos17.sale.enums.Status

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserResponse(
    var uuid: String?,
    var name: String,
    var cpf: String,
    var email: String,
    var active: Status,
)
