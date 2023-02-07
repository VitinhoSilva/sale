package com.jvprojetos17.sale.request

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class ProductQuantityRequest(

    @field:NotNull(message = "Product id must be informed!")
    var productId: String,

    @field:NotNull(message = "Quantity must be informed!")
    @field:Min(value = 1)
    var quantity: Int,
)
