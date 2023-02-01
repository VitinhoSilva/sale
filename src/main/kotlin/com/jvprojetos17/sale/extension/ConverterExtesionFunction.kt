package com.jvprojetos17.sale.extension

import com.jvprojetos17.sale.model.User
import com.jvprojetos17.sale.request.UserRequest
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

fun UserResponse.toUser(): User {
    return User(
        id = this.id,
        name = this.name,
        cpf = this.cpf,
        email = this.email,
        active = this.active
    )
}

fun UserRequest.toUser(): User {
    return User(
        name = this.name,
        cpf = this.cpf,
        email = this.email,
    )
}