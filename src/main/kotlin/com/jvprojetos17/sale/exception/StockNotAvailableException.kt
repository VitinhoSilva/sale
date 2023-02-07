package com.jvprojetos17.sale.exception

class StockNotAvailableException(override val message: String, val errorCode: String, val productsNotAvailable: List<String>) : Exception()
