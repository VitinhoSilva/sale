package com.jvprojetos17.sale.request

import javax.validation.constraints.NotEmpty

data class ProductStockRequest(

    @field:NotEmpty(message = "ProductId's and quantity's must be informed!")
    var productsAndQuantity: List<ProductQuantityRequest>,
)
