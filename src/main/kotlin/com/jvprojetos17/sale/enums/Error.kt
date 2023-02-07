package com.jvprojetos17.sale.enums

enum class Error(val code: String, val message: String) {

    S000("S-000", "Unauthorized!"),
    S001("S-001", "This user does not have permission for this resource!"),
    S002("S-002", "Invalid Request!"),

    S101("S-101", "User with id: [%s] not found!"),

    S204("S-204", "Product with id: [%s] not found!"),
    S207("S-207", "Product don't have enough stock!"),
}
