package com.jvprojetos17.sale.extension

import com.jvprojetos17.sale.model.Product
import com.jvprojetos17.sale.model.User
import com.jvprojetos17.sale.request.ProductRequest
import com.jvprojetos17.sale.request.UserRequest
import com.jvprojetos17.sale.response.ProductResponse
import com.jvprojetos17.sale.response.UserResponse


fun User.toResponse(): UserResponse {
    return UserResponse(
        id = id,
        name = name,
        cpf = cpf,
        email = email,
        active = active
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

fun Product.toResponse(): ProductResponse {
    return ProductResponse(
        id = this.id,
        description = this.description,
        code = this.code,
        price = this.price,
        stock = this.stock,
        active = this.active
    )
}

fun ProductResponse.toProduct(): Product {
    return Product(
        id = this.id,
        description = this.description,
        code = this.code,
        price = this.price,
        stock = this.stock,
        active = this.active
    )
}

fun ProductRequest.toProduct(): Product {
    return Product(
        description = this.description,
        code = this.code,
        price = this.price,
        stock = this.stock,
    )
}