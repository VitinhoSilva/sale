package com.jvprojetos17.sale.response

import com.jvprojetos17.sale.enums.Status

data class ProductResponse (
    var id: Long?,
    var description: String,
    var code: String,
    var price: Double,
    var stock: Int,
    var active: Status
)