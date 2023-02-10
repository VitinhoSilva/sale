package com.jvprojetos17.sale.controller

import com.jvprojetos17.sale.anotationCustom.PermissionAdmin
import com.jvprojetos17.sale.anotationCustom.PermissionAdminOrCustomer
import com.jvprojetos17.sale.enums.Status
import com.jvprojetos17.sale.extension.toResponse
import com.jvprojetos17.sale.request.ProductRequest
import com.jvprojetos17.sale.request.ProductStockRequest
import com.jvprojetos17.sale.response.ProductResponse
import com.jvprojetos17.sale.service.ProductService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/product")
class ProductController(
    private val productService: ProductService,
) {

    @GetMapping("/{productId}")
    @PermissionAdminOrCustomer
    fun getById(@PathVariable productId: String): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok().body(productService.findByUuid(productId)?.toResponse())
    }

    @PostMapping
    @PermissionAdmin
    fun save(
        @RequestBody @Valid
        productRequest: ProductRequest,
    ): ResponseEntity<HttpStatus> {
        productService.save(productRequest)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/situation")
    @PermissionAdmin
    fun getAllByActive(@RequestParam active: Status): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok().body(productService.getAllByActive(active))
    }

    @GetMapping("/filter")
    @PermissionAdminOrCustomer
    fun getFilter(
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC, size = 10) page: Pageable,
        @RequestParam(required = false) description: String?,
        @RequestParam(required = false) code: String?,
        @RequestParam(required = false, defaultValue = "ACTIVE") active: Status,
    ): ResponseEntity<Page<ProductResponse>> {
        return ResponseEntity.ok().body(productService.filter(page, description, code, active))
    }

    @PutMapping("/{productId}")
    @PermissionAdmin
    fun update(@PathVariable productId: String, @RequestBody productRequest: ProductRequest): ResponseEntity<HttpStatus> {
        productService.update(productId, productRequest)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PatchMapping("/{productId}/inactivate")
    @PermissionAdmin
    fun inactivate(@PathVariable productId: String): ResponseEntity<HttpStatus> {
        productService.inactivate(productId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PatchMapping("/{productId}/activate")
    @PermissionAdmin
    fun activate(@PathVariable productId: String): ResponseEntity<HttpStatus> {
        productService.activate(productId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PatchMapping("/add-stock")
    @PermissionAdmin
    fun addStock(
        @RequestBody @Valid
        productStockRequestList: ProductStockRequest,
    ): ResponseEntity<HttpStatus> {
        productService.addStock(productStockRequestList)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
