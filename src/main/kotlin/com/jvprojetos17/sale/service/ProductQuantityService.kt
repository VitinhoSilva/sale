package com.jvprojetos17.sale.service

import com.jvprojetos17.sale.model.ProductQuantity
import com.jvprojetos17.sale.model.Purchase
import com.jvprojetos17.sale.request.ProductQuantityRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductQuantityService(
    @Autowired val productService: ProductService,
) {

    fun setListProductAndQuantity(
        purchase: Purchase,
        productsAndQuantityRequestList: List<ProductQuantityRequest>
    ): List<ProductQuantity> {
        val productsAndQuantity = mutableListOf<ProductQuantity>()
        productsAndQuantityRequestList.map {
            productsAndQuantity.add(
                ProductQuantity(
                    purchase = purchase,
                    product = productService.findById(it.productId),
                    quantity = it.quantity
                )
            )
        }

        return productsAndQuantity
    }

}