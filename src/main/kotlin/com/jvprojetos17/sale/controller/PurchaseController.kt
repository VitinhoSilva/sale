package com.jvprojetos17.sale.controller

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
    val purchaseService: PurchaseService
) {
    @GetMapping("/{purchaseId}")
    fun getById(@PathVariable purchaseId: Long): ResponseEntity<PurchaseResponse> {
        return ResponseEntity.ok().body(purchaseService.findById(purchaseId))
    }

    @PostMapping
    fun save(@RequestBody @Valid purchaseRequest: PurchaseRequest): ResponseEntity<HttpStatus> {
        purchaseService.save(purchaseRequest)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

}