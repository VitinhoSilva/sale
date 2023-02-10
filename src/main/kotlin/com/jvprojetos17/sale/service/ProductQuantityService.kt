package com.jvprojetos17.sale.service

import com.jvprojetos17.sale.model.ProductQuantity
import com.jvprojetos17.sale.model.Purchase
import com.jvprojetos17.sale.request.ProductQuantityRequest
import org.springframework.stereotype.Service

@Service
class ProductQuantityService(
    private val productService: ProductService,
) {

    fun setListProductAndQuantity(
        purchase: Purchase,
        productsAndQuantityRequestList: List<ProductQuantityRequest>,
    ): List<ProductQuantity> {
        val productsAndQuantity = mutableListOf<ProductQuantity>()
        productsAndQuantityRequestList.map {
            val product = productService.findByUuid(it.productId)
            productsAndQuantity.add(
                ProductQuantity(
                    purchase = purchase,
                    product = product!!,
                    quantity = it.quantity,
                ),
            )
        }

        return productsAndQuantity
    }
}
