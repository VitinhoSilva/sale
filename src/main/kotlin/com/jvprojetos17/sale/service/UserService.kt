package com.jvprojetos17.sale.service

import com.jvprojetos17.sale.enums.Status
import com.jvprojetos17.sale.extension.toResponse
import com.jvprojetos17.sale.model.QUser
import com.jvprojetos17.sale.model.User
import com.jvprojetos17.sale.repository.UserRepository
import com.jvprojetos17.sale.response.UserResponse
import com.querydsl.core.BooleanBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class UserService(
    @Autowired
    val userRepository: UserRepository
) {

    fun findById(id: Long): UserResponse {
        return userRepository.findById(id).orElseThrow().toResponse()
    }

    fun save(userRequest: User) {
        userRepository.save(userRequest)
    }

    fun getAllActives(situation: Status): List<UserResponse> {
        return userRepository.findByActive(situation).map { it.toResponse() }
    }

    fun filter(
        page: Pageable,
        id: Long?,
        name: String?,
        cpf: String?,
        email: String?,
        active: Status
    ): Page<UserResponse> {

        val qUser: QUser = QUser.user
        val where = BooleanBuilder()

        id?.let { where.and(qUser.id.eq(it)) }
        name?.let { where.and(qUser.name.contains(it)) }
        cpf?.let { where.and(qUser.cpf.contains(it)) }
        email?.let { where.and(qUser.email.contains(it)) }

        return userRepository.findAll(where, page).let { it -> it.map { it.toResponse() } }
    }

}