package com.jvprojetos17.sale.service

import com.jvprojetos17.sale.enums.Error
import com.jvprojetos17.sale.enums.Status
import com.jvprojetos17.sale.exception.NotFoundException
import com.jvprojetos17.sale.extension.toEntity
import com.jvprojetos17.sale.extension.toResponse
import com.jvprojetos17.sale.model.Product
import com.jvprojetos17.sale.model.QProduct
import com.jvprojetos17.sale.repository.ProductRepository
import com.jvprojetos17.sale.request.ProductRequest
import com.jvprojetos17.sale.request.ProductStockRequest
import com.jvprojetos17.sale.response.ProductResponse
import com.querydsl.core.BooleanBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.Objects

@Service
class ProductService(
    private val productRepository: ProductRepository,
) {

    fun findByUuid(uuid: String): Product? {
        productRepository.findByUuid(uuid).run {
            return if (Objects.nonNull(this)) {
                this
            } else {
                throw NotFoundException(Error.S204.message.format(uuid), Error.S204.code)
            }
        }
    }

    fun save(productRequest: ProductRequest) {
        productRepository.save(productRequest.toEntity())
    }

    fun getAllByActive(situation: Status): List<ProductResponse> {
        return productRepository.findByActive(situation).map { it.toResponse() }
    }

    fun filter(
        page: Pageable,
        description: String?,
        code: String?,
        active: Status,
    ): Page<ProductResponse> {
        val qProduct: QProduct = QProduct.product
        val where = BooleanBuilder()

        description?.let { where.and(qProduct.description.contains(it)) }
        code?.let { where.and(qProduct.code.contains(it)) }
        active.let { where.and(qProduct.active.eq(it)) }

        return productRepository.findAll(where, page).let { it -> it.map { it.toResponse() } }
    }

    fun update(productId: String, productRequest: ProductRequest) {
        productRequest.toEntity().run {
            productRepository.save(
                copy(uuid = productId),
            )
        }
    }

    fun inactivate(productId: String) {
        findByUuid(productId)?.run {
            if (active != Status.FALSE) {
                productRepository.save(copy(active = Status.FALSE))
            }
        }
    }

    fun activate(productId: String) {
        findByUuid(productId)?.run {
            if (active != Status.TRUE) {
                productRepository.save(copy(active = Status.TRUE))
            }
        }
    }

    fun isAvailableStockByProductAndQuantity(productId: String, quantityId: Int): Boolean {
        return productRepository.checkAvailableStockByProductIdAndQuantity(productId, quantityId)
    }

    fun lowInStock(productId: String, quantityId: Int) {
        findByUuid(productId)?.run {
            productRepository.save(copy(stock = stock - quantityId))
        }
    }

    fun addStock(productStockRequest: ProductStockRequest) {
        productStockRequest.productsAndQuantity.map {
            findByUuid(it.productId)?.run {
                productRepository.save(copy(stock = stock + it.quantity))
            }
        }
    }
}
