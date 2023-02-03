package com.jvprojetos17.sale.service

import com.jvprojetos17.sale.exception.AuthenticationException
import com.jvprojetos17.sale.repository.UserRepository
import com.jvprojetos17.sale.security.UserCustomDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsCustomService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(id: String): UserDetails {
        val customer = userRepository.findById(id.toLong())
            .orElseThrow { AuthenticationException("Usuario não encontrado", "999") }
        return UserCustomDetails(customer)
    }
}