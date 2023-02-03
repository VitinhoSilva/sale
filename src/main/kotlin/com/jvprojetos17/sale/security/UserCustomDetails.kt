package com.jvprojetos17.sale.security

import com.jvprojetos17.sale.enums.Status
import com.jvprojetos17.sale.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCustomDetails(val user: User) : UserDetails {
    val id = user.id
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        user.profiles.map { SimpleGrantedAuthority(it.description) }.toMutableList()

    override fun getPassword(): String = user.password
    override fun getUsername(): String = user.id.toString()
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = user.active == Status.ACTIVE
}