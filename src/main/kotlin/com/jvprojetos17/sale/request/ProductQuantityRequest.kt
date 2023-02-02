package com.jvprojetos17.sale.request

import javax.validation.constraints.NotEmpty

data class ProductQuantityRequest(

    @field:NotEmpty(message = "Product id must be informed!")
    var productId: Long,

    @field:NotEmpty(message = "Quantity id must be informed!")
    var quantity: Int,
)