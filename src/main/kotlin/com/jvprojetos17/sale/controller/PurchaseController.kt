package com.jvprojetos17.sale.controller

import com.jvprojetos17.sale.anotationCustom.PermissionCustomer
import com.jvprojetos17.sale.request.PurchaseRequest
import com.jvprojetos17.sale.response.PurchaseResponse
import com.jvprojetos17.sale.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/purchase")
class PurchaseController(
    private val purchaseService: PurchaseService
) {

    @GetMapping("/{purchaseId}")
    @PermissionCustomer
    fun getById(@PathVariable purchaseId: Long): ResponseEntity<PurchaseResponse> {
        return ResponseEntity.ok().body(purchaseService.getById(purchaseId))
    }

    @PostMapping
    @PermissionCustomer
    fun save(@RequestBody @Valid purchaseRequest: PurchaseRequest): ResponseEntity<HttpStatus> {
        purchaseService.save(purchaseRequest)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/user/{userId}")
    @PermissionCustomer
    fun getByUserId(@PathVariable userId: Long): ResponseEntity<List<PurchaseResponse>> {
        return ResponseEntity.status(HttpStatus.OK).body(purchaseService.getByUserId(userId))
    }

}