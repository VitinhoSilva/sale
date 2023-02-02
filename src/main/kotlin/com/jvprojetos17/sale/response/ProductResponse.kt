package com.jvprojetos17.sale.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.jvprojetos17.sale.enums.Status

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductResponse(
    var id: Long? = null,
    var description: String? = null,
    var code: String? = null,
    var price: Double? = null,
    var stock: Int? = null,
    var active: Status? = null
)