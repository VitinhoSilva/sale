package com.jvprojetos17.sale.enums

enum class Errors(val code: String, val message: String) {

    S001("S-001", "Invalid Request!"),
    S101("S-001", "User with id: [%s] not found!"),
    S102("S-102", "User with email: [%s] is already inactive!"),
    S103("S-103", "User with email: [%s] is already active!"),
    S104("S-104", "Product with id: [%s] not found!"),
    S105("S-105", "Product with code: [%s] is already inactive!"),
    S106("S-106", "Product with code: [%s] is already active!"),
}