package com.jvprojetos17.sale.service

import com.jvprojetos17.sale.enums.Errors
import com.jvprojetos17.sale.exception.NotFoundException
import com.jvprojetos17.sale.exception.StockNotAvailableException
import com.jvprojetos17.sale.extension.toResponse
import com.jvprojetos17.sale.model.Purchase
import com.jvprojetos17.sale.repository.PurchaseRepository
import com.jvprojetos17.sale.request.ProductQuantityRequest
import com.jvprojetos17.sale.request.PurchaseRequest
import com.jvprojetos17.sale.response.PurchaseResponse
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val userService: UserService,
    private val productService: ProductService,
    private val productQuantityService: ProductQuantityService
) {

    fun findById(id: Long): Purchase {
        return purchaseRepository.findById(id)
            .orElseThrow { NotFoundException(Errors.S307.message.format(id), Errors.S307.code) }
    }

    fun getById(id: Long): PurchaseResponse {
        return purchaseRepository.findById(id)
            .orElseThrow { NotFoundException(Errors.S307.message.format(id), Errors.S307.code) }.toResponse()
    }

    fun calculateTotalPurchaseByProductPrice(productsAndQuantity: List<ProductQuantityRequest>): Double {
        return productsAndQuantity.sumByDouble { productService.findById(it.productId).price * it.quantity }
    }

    fun checkProductsAvailability(purchaseRequest: PurchaseRequest) {
        val productsCodeNotAvailable = mutableListOf<Long>()

        purchaseRequest.productsAndQuantity.forEach {
            if (!productService.isAvailableStockByProductAndQuantity(it.productId, it.quantity)) {
                productsCodeNotAvailable.add(it.productId)
            }
        }

        if (productsCodeNotAvailable.size > 0) {
            throw StockNotAvailableException(Errors.S207.message, Errors.S207.code, productsCodeNotAvailable)
        }
    }

    fun save(purchaseRequest: PurchaseRequest) {
        checkProductsAvailability(purchaseRequest)
        
        val user = userService.findById(purchaseRequest.userId)
        val total = calculateTotalPurchaseByProductPrice(purchaseRequest.productsAndQuantity)
        val purchase = Purchase(
            user = user,
            total = total
        )

        purchase.products =
            productQuantityService.setListProductAndQuantity(purchase, purchaseRequest.productsAndQuantity)

        purchaseRepository.save(purchase)
        purchaseRequest.productsAndQuantity.map { productService.lowInStock(it.productId, it.quantity) }

    }

    fun getByUserId(userId: Long): List<PurchaseResponse> {
        return purchaseRepository.findByUserId(userId).run {
            this.map { it.toResponse() }
        }
    }

}