package com.jvprojetos17.sale.service

import com.jvprojetos17.sale.exception.AuthenticationException
import com.jvprojetos17.sale.repository.UserRepository
import com.jvprojetos17.sale.security.UserCustomDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserDetailsCustomService(
    private val userRepository: UserRepository,
) : UserDetailsService {
    override fun loadUserByUsername(uuid: String): UserDetails {
        val user = userRepository.findByUuid(uuid)
        if (Objects.isNull(user)) throw AuthenticationException("Usuario não encontrado!", "999")
        return UserCustomDetails(user)
    }
}
