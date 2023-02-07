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
        uuid = this.uuid,
        name = this.name,
        cpf = this.cpf,
        email = this.email,
        active = this.active,
    )
}

fun UserRequest.toEntity(): User {
    return User(
        name = this.name,
        cpf = this.cpf,
        email = this.email,
        password = this.password,
    )
}

fun Product.toResponse(): ProductResponse {
    return ProductResponse(
        uuid = this.uuid,
        description = this.description,
        code = this.code,
        price = this.price,
        stock = this.stock,
        active = this.active,
    )
}

fun Product.toResponseBasic(): ProductResponse {
    return ProductResponse(
        uuid = this.uuid,
        description = this.description,
        code = this.code,
        price = this.price,
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
        uuid = this.uuid,
        product = this.product.toResponseBasic(),
        quantity = this.quantity,
    )
}

fun Purchase.toResponse(): PurchaseResponse {
    return PurchaseResponse(
        uuid = this.uuid,
        user = this.user.toResponse(),
        products = this.products.map { it.toResponse() },
        total = this.total,
        situation = this.situation,
        createAt = this.createAt,
    )
}
