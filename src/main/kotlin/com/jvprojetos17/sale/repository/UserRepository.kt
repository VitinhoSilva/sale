package com.jvprojetos17.sale.repository

import com.jvprojetos17.sale.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>