package com.jvprojetos17.sale.service

import com.jvprojetos17.sale.enums.Error
import com.jvprojetos17.sale.enums.Profile
import com.jvprojetos17.sale.enums.Status
import com.jvprojetos17.sale.exception.BusinessException
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

@Service
class UserService(
    private val userRepository: UserRepository,
    private val bCrypt: BCryptPasswordEncoder
) {

    fun findById(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { NotFoundException(Error.S101.message.format(id), Error.S101.code) }
    }

    fun getById(id: Long): UserResponse {
        return userRepository.findById(id)
            .orElseThrow { NotFoundException(Error.S101.message.format(id), Error.S101.code) }.toResponse()
    }

    fun save(userRequest: UserRequest) {
        userRequest.toEntity().run {
            userRepository.save(
                copy(
                    profiles = setOf(Profile.CUSTOMER),
                    password = bCrypt.encode(userRequest.password)
                )
            )
        }

    }

    fun getAllActives(situation: Status): List<UserResponse> {
        return userRepository.findByActive(situation).map { it.toResponse() }
    }

    fun filter(
        page: Pageable, id: Long?, name: String?, cpf: String?, email: String?, active: Status
    ): Page<UserResponse> {

        val qUser: QUser = QUser.user
        val where = BooleanBuilder()

        id?.let { where.and(qUser.id.eq(it)) }
        name?.let { where.and(qUser.name.contains(it)) }
        cpf?.let { where.and(qUser.cpf.contains(it)) }
        email?.let { where.and(qUser.email.contains(it)) }
        active.let { where.and(qUser.active.eq(it)) }

        return userRepository.findAll(where, page).let { it -> it.map { it.toResponse() } }
    }

    fun update(userId: Long, userRequest: UserRequest) {
        userRequest.toEntity().run {
            userRepository.save(
                copy(id = userId)
            )
        }
    }

    fun inactivate(userId: Long) {
        findById(userId).run {
            if (active != Status.INACTIVE) {
                userRepository.save(copy(active = Status.INACTIVE))
            }
        }
    }

    fun activate(userId: Long) {
        findById(userId).run {
            if (active != Status.ACTIVE) {
                userRepository.save(copy(active = Status.ACTIVE))
            }
        }
    }
}