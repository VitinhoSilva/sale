package com.jvprojetos17.sale.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jvprojetos17.sale.exception.AuthenticationException
import com.jvprojetos17.sale.repository.UserRepository
import com.jvprojetos17.sale.request.LoginRequest
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil,
) : UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val loginRequest = jacksonObjectMapper().readValue(request.inputStream, LoginRequest::class.java)
            val uuid = userRepository.findByCpf(loginRequest.cpf)?.uuid
            val authToken = UsernamePasswordAuthenticationToken(uuid, loginRequest.password)
            return authenticationManager.authenticate(authToken)
        } catch (ex: Exception) {
            throw AuthenticationException("Falha ao autenticar", "999")
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication,
    ) {
        val uuid = (authResult.principal as UserCustomDetails).uuid
        val token = uuid.let { jwtUtil.generateToken(uuid) }
        response.addHeader("Authorization", "Bearer $token")
    }
}
