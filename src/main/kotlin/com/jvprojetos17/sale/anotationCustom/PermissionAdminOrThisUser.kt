package com.jvprojetos17.sale.anotationCustom

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasAnyRole('ADMIN') || #id == authentication.principal.uuid")
annotation class PermissionAdminOrThisUser
