package com.jvprojetos17.sale.service

import com.jvprojetos17.sale.enums.Errors
import com.jvprojetos17.sale.enums.Status
import com.jvprojetos17.sale.exception.BusinessException
import com.jvprojetos17.sale.exception.NotFoundException
import com.jvprojetos17.sale.extension.toResponse
import com.jvprojetos17.sale.extension.toUser
import com.jvprojetos17.sale.model.QUser
import com.jvprojetos17.sale.repository.UserRepository
import com.jvprojetos17.sale.request.UserRequest
import com.jvprojetos17.sale.response.UserResponse
import com.querydsl.core.BooleanBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired val userRepository: UserRepository
) {

    fun findById(id: Long): UserResponse {
        return userRepository.findById(id)
            .orElseThrow { NotFoundException(Errors.S101.message.format(id), Errors.S101.code) }.toResponse()
    }

    fun save(userRequest: UserRequest) {
        userRepository.save(userRequest.toUser())
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

        return userRepository.findAll(where, page).let { it -> it.map { it.toResponse() } }
    }

    fun update(userId: Long, userRequest: UserRequest) {
        findById(userId)
        userRequest.toUser().run {
            userRepository.save(
                copy(id = userId)
            )
        }
    }

    fun inactivate(userId: Long) {
        findById(userId).toUser().run {
            if (active == Status.INACTIVE) {
                throw BusinessException(Errors.S102.message.format(email), Errors.S102.code)
            } else {
                userRepository.save(copy(active = Status.INACTIVE))
            }
        }
    }

    fun activate(userId: Long) {
        findById(userId).toUser().run {
            if (active == Status.ACTIVE) {
                throw BusinessException(Errors.S103.message.format(email), Errors.S103.code)
            } else {
                userRepository.save(copy(active = Status.ACTIVE))
            }
        }
    }
}