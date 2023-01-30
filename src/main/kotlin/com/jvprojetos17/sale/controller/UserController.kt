package com.jvprojetos17.sale.controller

import com.jvprojetos17.sale.enums.Status
import com.jvprojetos17.sale.model.User
import com.jvprojetos17.sale.response.UserResponse
import com.jvprojetos17.sale.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("users")
class UserController(
    val userService: UserService
) {

    @GetMapping("/{userId}")
    fun getById(@PathVariable userId: Long): ResponseEntity<UserResponse> {
        return ResponseEntity.ok().body(userService.findById(userId))
    }

    @PostMapping
    fun save(@RequestBody userRequest: User): ResponseEntity<HttpStatus> {
        userService.save(userRequest)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/situation")
    fun getAllBySituation(@RequestParam situation: Status): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok().body(userService.getAllActives(situation))
    }

    @GetMapping("/filter")
    fun getFilter(
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC, size = 10) page: Pageable,
        @RequestParam(required = false) id: Long?,
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) cpf: String?,
        @RequestParam(required = false) email: String?,
        @RequestParam(required = false, defaultValue = "ACTIVE") active: Status,
    ) : ResponseEntity<Page<UserResponse>> {
        return ResponseEntity.ok().body(userService.filter(page, id, name, cpf, email, active))
    }

}