package com.jvprojetos17.sale.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class FieldErrorResponse(
    var messsage: String,
    var field: String? = null,
)
