package com.jvprojetos17.sale.repository

import com.jvprojetos17.sale.model.ProductQuantity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductQuantityRepository : JpaRepository<ProductQuantity, Long> {

}