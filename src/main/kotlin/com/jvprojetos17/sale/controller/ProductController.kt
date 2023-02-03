package com.jvprojetos17.sale.controller

import com.jvprojetos17.sale.enums.Status
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
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/product")
class ProductController(
    val productService: ProductService
) {

    @GetMapping("/{productId}")
    fun getById(@PathVariable productId: Long): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok().body(productService.getById(productId))
    }

    @PostMapping
    fun save(@RequestBody @Valid productRequest: ProductRequest): ResponseEntity<HttpStatus> {
        productService.save(productRequest)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/situation")
    fun getAllBySituation(@RequestParam situation: Status): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok().body(productService.getAllActives(situation))
    }

    @GetMapping("/filter")
    fun getFilter(
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC, size = 10) page: Pageable,
        @RequestParam(required = false) id: Long?,
        @RequestParam(required = false) description: String?,
        @RequestParam(required = false) code: String?,
        @RequestParam(required = false, defaultValue = "ACTIVE") active: Status,
    ): ResponseEntity<Page<ProductResponse>> {
        return ResponseEntity.ok().body(productService.filter(page, id, description, code, active))
    }

    @PutMapping("/{productId}")
    fun update(@PathVariable productId: Long, @RequestBody productRequest: ProductRequest): ResponseEntity<HttpStatus> {
        productService.update(productId, productRequest)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PutMapping("/inactivate/{productId}")
    fun inactivate(@PathVariable productId: Long): ResponseEntity<HttpStatus> {
        productService.inactivate(productId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PutMapping("/activate/{productId}")
    fun activate(@PathVariable productId: Long): ResponseEntity<HttpStatus> {
        productService.activate(productId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PutMapping("/add-stock")
    fun addStock(@RequestBody @Valid productStockRequestList: ProductStockRequest): ResponseEntity<HttpStatus> {
        productService.addStock(productStockRequestList)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}