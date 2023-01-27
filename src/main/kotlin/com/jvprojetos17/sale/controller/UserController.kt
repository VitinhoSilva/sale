package com.jvprojetos17.sale.controller

import com.jvprojetos17.sale.model.User
import com.jvprojetos17.sale.response.UserResponse
import com.jvprojetos17.sale.service.UserService
import com.jvprojetos17.sale.extension.toResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("users")
class UserController (
    val userService: UserService
){

    @GetMapping("/{userId}")
    fun getById(@PathVariable userId: Long): ResponseEntity<UserResponse> {
        return ResponseEntity.ok().body(userService.findById(userId).toResponse())
    }

    @PostMapping
    fun save(@RequestBody userRequest: User): ResponseEntity<HttpStatus> {
        userService.save(userRequest)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

}