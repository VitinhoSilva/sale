package com.jvprojetos17.sale.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.UUID

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductQuantityResponse(
    var uuid: UUID?,
    var product: ProductResponse,
    var quantity: Int,
)
