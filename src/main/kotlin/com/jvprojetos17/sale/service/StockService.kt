package com.jvprojetos17.sale.service

import com.jvprojetos17.sale.repository.StockRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StockService(
    private val stockRepository: StockRepository
) {

    fun isAvailableStockByProductAndQuantity(productId: Long, quantityId: Int): Boolean {
        return stockRepository.checkAvailableStockByProductAndQuantity(productId, quantityId)
    }

}