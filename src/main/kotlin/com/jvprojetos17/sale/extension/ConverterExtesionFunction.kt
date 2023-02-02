package com.jvprojetos17.sale.extension

import com.jvprojetos17.sale.model.Product
import com.jvprojetos17.sale.model.ProductQuantity
import com.jvprojetos17.sale.model.Purchase
import com.jvprojetos17.sale.model.User
import com.jvprojetos17.sale.request.ProductRequest
import com.jvprojetos17.sale.request.UserRequest
import com.jvprojetos17.sale.response.ProductQuantityResponse
import com.jvprojetos17.sale.response.ProductResponse
import com.jvprojetos17.sale.response.PurchaseResponse
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

fun UserResponse.toEntity(): User {
    return User(
        id = this.id,
        name = this.name,
        cpf = this.cpf,
        email = this.email,
        active = this.active
    )
}

fun UserRequest.toEntity(): User {
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

fun Product.toResponseBasic(): ProductResponse {
    return ProductResponse(
        id = this.id,
        description = this.description,
        code = this.code,
        price = this.price
    )
}

fun ProductRequest.toEntity(): Product {
    return Product(
        description = this.description,
        code = this.code,
        price = this.price,
        stock = this.stock,
    )
}

fun ProductQuantity.toResponse(): ProductQuantityResponse {
    return ProductQuantityResponse(
        id = this.id,
        product = this.product.toResponseBasic(),
        quantity = this.quantity
    )
}

fun Purchase.toResponse(): PurchaseResponse {
    return PurchaseResponse(
        id = this.id,
        user = this.user.toResponse(),
        products = this.products.map { it.toResponse() },
        total = this.total,
        situation = this.situation,
        createAt = this.createAt
    )
}