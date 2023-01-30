package com.jvprojetos17.sale.repository

import com.jvprojetos17.sale.enums.Status
import com.jvprojetos17.sale.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository


@Repository
interface UserRepository : JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {
    fun findByActive(active: Status): Set<User>
}