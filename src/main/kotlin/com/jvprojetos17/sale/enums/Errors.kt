package com.jvprojetos17.sale.enums

enum class Errors(val code: String, val message: String) {

    S001("S-001", "Invalid Request!"),
    S101("S-001", "User with id: [%s] not found!"),
    S102("S-102", "User with email: [%s] is already inactive!"),
    S103("S-101", "User with email: [%s] is already active!"),
}