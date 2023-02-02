package com.jvprojetos17.sale.repository

import com.jvprojetos17.sale.model.Stock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface StockRepository : JpaRepository<Stock, Long>, QuerydslPredicateExecutor<Stock> {

    @Query(nativeQuery = true, value = "SELECT " +
                "CASE WHEN COUNT(*) >= 1 THEN 'TRUE' ELSE 'FALSE' END AS ISAVAILABLE " +
                "FROM " +
                "stock s " +
                "WHERE " +
                "s.product_id = :productId " +
                "AND :quantity <= s.availableQuantity")
    fun checkAvailableStockByProductAndQuantity(
        @Param("productId") productId: Long,
        @Param("quantity") quantity: Int
    ): Boolean
}