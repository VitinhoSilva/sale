package com.jvprojetos17.sale.service

import com.jvprojetos17.sale.enums.Errors
import com.jvprojetos17.sale.enums.Status
import com.jvprojetos17.sale.exception.BusinessException
import com.jvprojetos17.sale.exception.NotFoundException
import com.jvprojetos17.sale.extension.toProduct
import com.jvprojetos17.sale.extension.toResponse
import com.jvprojetos17.sale.model.QProduct
import com.jvprojetos17.sale.repository.ProductRepository
import com.jvprojetos17.sale.request.ProductRequest
import com.jvprojetos17.sale.response.ProductResponse
import com.querydsl.core.BooleanBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductService(
    @Autowired val productRepository: ProductRepository
) {

    fun findById(id: Long): ProductResponse {
        return productRepository.findById(id)
            .orElseThrow { NotFoundException(Errors.S101.message.format(id), Errors.S101.code) }.toResponse()
    }

    fun save(productRequest: ProductRequest) {
        productRepository.save(productRequest.toProduct())
    }

    fun getAllActives(situation: Status): List<ProductResponse> {
        return productRepository.findByActive(situation).map { it.toResponse() }
    }

    fun filter(
        page: Pageable, id: Long?, description: String?, code: String?, active: Status
    ): Page<ProductResponse> {

        val qProduct: QProduct = QProduct.product
        val where = BooleanBuilder()

        id?.let { where.and(qProduct.id.eq(it)) }
        description?.let { where.and(qProduct.description.contains(it)) }
        code?.let { where.and(qProduct.code.contains(it)) }
        active.let { where.and(qProduct.active.eq(it)) }

        return productRepository.findAll(where, page).let { it -> it.map { it.toResponse() } }
    }

    fun update(productId: Long, productRequest: ProductRequest) {
        findById(productId)
        productRequest.toProduct().run {
            productRepository.save(
                copy(id = productId)
            )
        }
    }

    fun inactivate(productId: Long) {
        findById(productId).toProduct().run {
            if (active == Status.INACTIVE) {
                throw BusinessException(Errors.S105.message.format(code), Errors.S105.code)
            } else {
                productRepository.save(copy(active = Status.INACTIVE))
            }
        }
    }

    fun activate(productId: Long) {
        findById(productId).toProduct().run {
            if (active == Status.ACTIVE) {
                throw BusinessException(Errors.S106.message.format(code), Errors.S106.code)
            } else {
                productRepository.save(copy(active = Status.ACTIVE))
            }
        }
    }
}