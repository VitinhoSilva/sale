package com.jvprojetos17.sale.extension

import com.jvprojetos17.sale.model.User
import com.jvprojetos17.sale.response.UserResponse


fun User.toResponse(): UserResponse {
    return UserResponse(
        id = this.id,
        name = this.name,
        cpf = this.cpf,
        email = this.email,
        active = this.active
    )
}