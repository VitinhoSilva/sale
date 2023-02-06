package com.jvprojetos17.sale.controller

import com.jvprojetos17.sale.anotationCustom.PermissionAdmin
import com.jvprojetos17.sale.anotationCustom.PermissionThisUser
import com.jvprojetos17.sale.anotationCustom.PermissionAdminOrThisUser
import com.jvprojetos17.sale.enums.Status
import com.jvprojetos17.sale.request.UserRequest
import com.jvprojetos17.sale.response.UserResponse
import com.jvprojetos17.sale.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{userId}")
    @PermissionAdminOrThisUser
    fun getById(@PathVariable userId: Long): ResponseEntity<UserResponse> {
        return ResponseEntity.ok().body(userService.getById(userId))
    }

    @PostMapping
    fun save(@RequestBody @Valid userRequest: UserRequest): ResponseEntity<HttpStatus> {
        userService.save(userRequest)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/situation")
    @PermissionAdmin
    fun getAllBySituation(@RequestParam situation: Status): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok().body(userService.getAllActives(situation))
    }

    @GetMapping("/filter")
    @PermissionAdmin
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun getFilter(
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC, size = 10) page: Pageable,
        @RequestParam(required = false) id: Long?,
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) cpf: String?,
        @RequestParam(required = false) email: String?,
        @RequestParam(required = false, defaultValue = "ACTIVE") active: Status,
    ): ResponseEntity<Page<UserResponse>> {
        return ResponseEntity.ok().body(userService.filter(page, id, name, cpf, email, active))
    }

    @PutMapping("/{userId}")
    @PermissionThisUser
    fun update(@PathVariable userId: Long, @RequestBody userRequest: UserRequest): ResponseEntity<HttpStatus> {
        userService.update(userId, userRequest)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PutMapping("/inactivate/{userId}")
    @PermissionThisUser
    fun inactivate(@PathVariable userId: Long): ResponseEntity<HttpStatus> {
        userService.inactivate(userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PutMapping("/activate/{userId}")
    @PermissionAdmin
    fun activate(@PathVariable userId: Long): ResponseEntity<HttpStatus> {
        userService.activate(userId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}