package com.jvprojetos17.sale.enums

enum class Error(val code: String, val message: String) {

    S000("S-000", "Unauthorized!"),
    S001("S-001", "This user does not have permission for this resource!"),
    S002("S-002", "Invalid Request!"),

    S101("S-101", "User with id: [%s] not found!"),
    S102("S-102", "User with email: [%s] is already inactive!"),
    S103("S-103", "User with email: [%s] is already active!"),

    S204("S-204", "Product with id: [%s] not found!"),
    S205("S-205", "Product with code: [%s] is already inactive!"),
    S206("S-206", "Product with code: [%s] is already active!"),
    S207("S-207", "Product don't have enough stock!"),

    S307("S-307", "Purchase with id: [%s] not found!"),
    S308("S-308", "Purchase with code: [%s] is already canceled!"),

}