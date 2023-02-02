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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    @Autowired val purchaseRepository: PurchaseRepository,
    @Autowired val stockService: StockService,
    @Autowired val userService: UserService,
    @Autowired val productService: ProductService,
    @Autowired val productQuantityService: ProductQuantityService
) {
    fun findById(id: Long): PurchaseResponse {
        return purchaseRepository.findById(id)
            .orElseThrow { NotFoundException(Errors.S307.message.format(id), Errors.S307.code) }.toResponse()
    }

    fun calculateTotalPurchaseByProductPrice(productsAndQuantity: List<ProductQuantityRequest>): Double {
        return productsAndQuantity.sumByDouble { productService.findById(it.productId).price * it.quantity }
    }

    fun checkProductsAvailability(purchaseRequest: PurchaseRequest) {
        val productsCodeNotAvailable = mutableListOf<Long>()

        purchaseRequest.productsAndQuantity.forEach {
            if (!stockService.isAvailableStockByProductAndQuantity(it.productId, it.quantity)) {
                productsCodeNotAvailable.add(it.productId)
            }
        }

        if (productsCodeNotAvailable.size > 0) {
            throw StockNotAvailableException(Errors.S207.message, Errors.S207.code, productsCodeNotAvailable)
        }
    }

    fun save(purchaseRequest: PurchaseRequest) {
        //1 - verificar se usuário existe - OK
        //2 - verificar se os produtos existem e estão disponíveis na quantidade suficientes para esta compra - OK
        //3 - Calcular total de cada produto * quantidade de cada - OK
        //4 - Salvar compra com situação: CONFIRMED quando tudo ocorrer bem - OK
        //5 - Dar baixa no stock deste produto -

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

    }

}