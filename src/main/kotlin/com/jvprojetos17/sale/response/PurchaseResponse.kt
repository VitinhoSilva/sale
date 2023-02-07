package com.jvprojetos17.sale.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.jvprojetos17.sale.enums.Situation
import java.time.LocalDate
import java.util.UUID

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PurchaseResponse(
    var uuid: UUID?,
    var user: UserResponse,
    var products: List<ProductQuantityResponse>,
    var total: Double,
    var situation: Situation,
    var createAt: LocalDate,
)
