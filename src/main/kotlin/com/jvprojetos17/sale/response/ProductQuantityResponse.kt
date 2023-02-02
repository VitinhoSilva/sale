package com.jvprojetos17.sale.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.*

@JsonInclude(NON_NULL)
data class ProductQuantityResponse(
    var id: Long?,
    var product: ProductResponse,
    var quantity: Int,
)