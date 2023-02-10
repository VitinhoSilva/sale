package com.jvprojetos17.sale.controller

import com.jvprojetos17.sale.anotationCustom.PermissionAdmin
import com.jvprojetos17.sale.anotationCustom.PermissionAdminOrThisUser
import com.jvprojetos17.sale.anotationCustom.PermissionThisUser
import com.jvprojetos17.sale.enums.Status
import com.jvprojetos17.sale.extension.toEntity
import com.jvprojetos17.sale.extension.toResponse
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
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
) {

    @GetMapping("/{id}")
    @PermissionAdminOrThisUser
    fun getById(@PathVariable id: String): ResponseEntity<UserResponse> {
        return ResponseEntity.ok().body(userService.findByUuid(id)?.toResponse())
    }

    @PostMapping
    fun save(
        @RequestBody @Valid
        userRequest: UserRequest,
    ): ResponseEntity<HttpStatus> {
        userService.save(userRequest.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/situation")
    @PermissionAdmin
    fun getAllBySituation(@RequestParam situation: Status): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok().body(userService.getAllByActive(situation))
    }

    @GetMapping("/filter")
    @PermissionAdmin
    @PreAuthorize("hasAnyRole('ADMIN')")
    fun getFilter(
        @PageableDefault(sort = ["uuid"], direction = Sort.Direction.DESC, size = 10) page: Pageable,
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) cpf: String?,
        @RequestParam(required = false) email: String?,
        @RequestParam(required = false, defaultValue = "ACTIVE") active: Status,
    ): ResponseEntity<Page<UserResponse>> {
        return ResponseEntity.ok().body(userService.filter(page, name, cpf, email, active))
    }

    @PutMapping("/{id}")
    @PermissionThisUser
    fun update(@PathVariable id: String, @RequestBody userRequest: UserRequest): ResponseEntity<HttpStatus> {
        userService.update(id, userRequest)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PatchMapping("/{id}/inactivate")
    @PermissionThisUser
    fun inactivate(@PathVariable id: String): ResponseEntity<HttpStatus> {
        userService.inactivate(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

    @PatchMapping("/{id}/activate")
    @PermissionAdmin
    fun activate(@PathVariable id: String): ResponseEntity<HttpStatus> {
        userService.activate(id )
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}
