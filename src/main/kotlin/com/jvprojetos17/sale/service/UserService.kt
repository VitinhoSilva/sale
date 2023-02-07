package com.jvprojetos17.sale.service

import com.jvprojetos17.sale.enums.Error
import com.jvprojetos17.sale.enums.Profile
import com.jvprojetos17.sale.enums.Status
import com.jvprojetos17.sale.exception.NotFoundException
import com.jvprojetos17.sale.extension.toEntity
import com.jvprojetos17.sale.extension.toResponse
import com.jvprojetos17.sale.model.QUser
import com.jvprojetos17.sale.model.User
import com.jvprojetos17.sale.repository.UserRepository
import com.jvprojetos17.sale.request.UserRequest
import com.jvprojetos17.sale.response.UserResponse
import com.querydsl.core.BooleanBuilder
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.Objects
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
    private val bCrypt: BCryptPasswordEncoder,
) {

    fun findByUuid(uuid: String): User {
        userRepository.findByUuid(uuid).run {
            return if (Objects.nonNull(this)) {
                this
            } else {
                throw NotFoundException(Error.S204.message.format(uuid), Error.S204.code)
            }
        }
    }

    fun getById(id: String): UserResponse {
        return userRepository.findById(id)
            .orElseThrow { NotFoundException(Error.S101.message.format(id), Error.S101.code) }.toResponse()
    }

    fun save(userRequest: UserRequest) {
        userRequest.toEntity().run {
            userRepository.save(
                copy(
                    uuid = UUID.randomUUID().toString(),
                    profiles = setOf(Profile.CUSTOMER),
                    password = bCrypt.encode(userRequest.password),
                ),
            )
        }
    }

    fun getAllActives(situation: Status): List<UserResponse> {
        return userRepository.findByActive(situation).map { it.toResponse() }
    }

    fun filter(
        page: Pageable,
        name: String?,
        cpf: String?,
        email: String?,
        active: Status,
    ): Page<UserResponse> {
        val qUser: QUser = QUser.user
        val where = BooleanBuilder()

        name?.let { where.and(qUser.name.contains(it)) }
        cpf?.let { where.and(qUser.cpf.contains(it)) }
        email?.let { where.and(qUser.email.contains(it)) }
        active.let { where.and(qUser.active.eq(it)) }

        return userRepository.findAll(where, page).let { it -> it.map { it.toResponse() } }
    }

    fun update(userId: String, userRequest: UserRequest) {
        userRequest.toEntity().run {
            userRepository.save(
                copy(uuid = userId),
            )
        }
    }

    fun inactivate(userId: String) {
        findByUuid(userId).run {
            if (active != Status.FALSE) {
                userRepository.save(copy(active = Status.FALSE))
            }
        }
    }

    fun activate(userId: String) {
        findByUuid(userId).run {
            if (active != Status.TRUE) {
                userRepository.save(copy(active = Status.TRUE))
            }
        }
    }
}
