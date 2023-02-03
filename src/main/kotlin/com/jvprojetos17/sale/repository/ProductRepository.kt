package com.jvprojetos17.sale.repository

import com.jvprojetos17.sale.enums.Status
import com.jvprojetos17.sale.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository


@Repository
interface ProductRepository : JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product> {
    fun findByActive(active: Status): Set<Product>

    @Query(
        nativeQuery = true, value = "SELECT " +
                "CASE WHEN COUNT(*) >= 1 THEN 'TRUE' ELSE 'FALSE' END AS ISAVAILABLE " +
                "FROM " +
                "product p " +
                "WHERE " +
                "p.id = :productId " +
                "AND :quantity <= p.stock"
    ) fun checkAvailableStockByProductAndQuantity(
        @Param("productId") productId: Long,
        @Param("quantity") quantity: Int
    ): Boolean
}