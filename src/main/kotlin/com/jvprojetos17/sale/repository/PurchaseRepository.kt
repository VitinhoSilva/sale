package com.jvprojetos17.sale.repository

import com.jvprojetos17.sale.model.Purchase
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface PurchaseRepository : JpaRepository<Purchase, String>, QuerydslPredicateExecutor<Purchase> {
    fun findByUserUuid(userId: Long): List<Purchase>
    fun findByUuid(uuid: String): Purchase
}
