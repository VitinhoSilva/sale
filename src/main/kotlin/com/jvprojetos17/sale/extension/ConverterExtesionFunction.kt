package com.jvprojetos17.sale.extension

import com.jvprojetos17.sale.model.Product
import com.jvprojetos17.sale.model.User
import com.jvprojetos17.sale.request.ProductRequest
import com.jvprojetos17.sale.response.ProductResponse


fun User.toResponse(): ProductResponse {
    return ProductResponse(
        id = id,
        name = name,
        cpf = cpf,
        email = email,
        active = active
    )
}

fun ProductResponse.toUser(): User {
    return User(
        id = this.id,
        name = this.name,
        cpf = this.cpf,
        email = this.email,
        active = this.active
    )
}

fun ProductRequest.toUser(): User {
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