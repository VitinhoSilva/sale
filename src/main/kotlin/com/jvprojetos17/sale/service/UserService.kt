package com.jvprojetos17.sale.service

import com.jvprojetos17.sale.model.User
import com.jvprojetos17.sale.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    val userRepository : UserRepository
) {

    fun findById(id: Long): User {
        val user = userRepository.findById(id).orElseThrow()
        return userRepository.findById(id).orElseThrow()
    }

    fun save(userRequest: User) {
        userRepository.save(userRequest)
    }

}