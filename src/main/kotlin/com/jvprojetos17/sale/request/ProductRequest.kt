package com.jvprojetos17.sale.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class ProductRequest(

    @field:NotEmpty(message = "Description must be informed!")
    var description: String,

    @field:NotEmpty(message = "Code must be informed!")
    var code: String,

    @field:NotNull(message = "Price must be informed!")
    var price: Double,

    @field:NotNull(message = "Stock must be informed!")
    var stock: Int,
)
