package com.jvprojetos17.sale.service

import com.jvprojetos17.sale.enums.Error
import com.jvprojetos17.sale.exception.NotFoundException
import com.jvprojetos17.sale.exception.StockNotAvailableException
import com.jvprojetos17.sale.extension.toResponse
import com.jvprojetos17.sale.model.Purchase
import com.jvprojetos17.sale.repository.PurchaseRepository
import com.jvprojetos17.sale.request.ProductQuantityRequest
import com.jvprojetos17.sale.request.PurchaseRequest
import com.jvprojetos17.sale.response.PurchaseResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val userService: UserService,
    private val productService: ProductService,
    private val productQuantityService: ProductQuantityService,
) {

    fun findByUuid(uuid: String): Purchase {
        purchaseRepository.findByUuid(uuid).run {
            return if (Objects.nonNull(this)) {
                this
            } else {
                throw NotFoundException(Error.S204.message.format(uuid), Error.S204.code)
            }
        }
    }

    fun getById(id: String): PurchaseResponse {
        return findByUuid(id).toResponse()
    }

    fun calculateTotalPurchaseByProductPrice(productsAndQuantity: List<ProductQuantityRequest>): Double {
        return productsAndQuantity.sumByDouble { productService.findByUuid(it.productId)?.price!! * it.quantity }
    }

    fun checkProductsAvailability(purchaseRequest: PurchaseRequest) {
        val productsCodeNotAvailable = mutableListOf<String>()

        purchaseRequest.productsAndQuantity.forEach {
            if (!productService.isAvailableStockByProductAndQuantity(it.productId, it.quantity)) {
                productsCodeNotAvailable.add(it.productId)
            }
        }

        if (productsCodeNotAvailable.size > 0) {
            throw StockNotAvailableException(Error.S205.message, Error.S205.code, productsCodeNotAvailable)
        }
    }

    fun save(purchaseRequest: PurchaseRequest) {
        checkProductsAvailability(purchaseRequest)

        val user = userService.findByUuid(purchaseRequest.userId)
        val total = calculateTotalPurchaseByProductPrice(purchaseRequest.productsAndQuantity)
        val purchase = user?.let {
            Purchase(
                user = it,
                total = total,
            )
        }

        purchase?.products =
            productQuantityService.setListProductAndQuantity(purchase!!, purchaseRequest.productsAndQuantity)

        purchaseRepository.save(purchase)
        purchaseRequest.productsAndQuantity.map { productService.lowInStock(it.productId, it.quantity) }
    }

    fun getByUserId(userId: Long): List<PurchaseResponse> {
        return purchaseRepository.findByUserUuid(userId).run {
            this.map { it.toResponse() }
        }
    }
}
