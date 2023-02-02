package com.jvprojetos17.sale.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class PurchaseRequest(

    @field:NotNull(message = "User id must be informed!")
    var userId: Long,

    @field:NotEmpty(message = "Product and quantity must be informed!")
    var productsAndQuantity: List<ProductQuantityRequest>,
)
