package com.jvprojetos17.sale.model

import com.jvprojetos17.sale.enums.Profile
import com.jvprojetos17.sale.enums.Status
import org.hibernate.validator.constraints.br.CPF
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@Entity(name = "user")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    @Size(max = 255)
    @NotEmpty(message = "Enter the user name!")
    var name: String,

    @Column
    @CPF
    @NotEmpty(message = "Enter the user cpf!")
    var cpf: String,

    @Column
    @Email
    @NotEmpty(message = "Enter the user email!")
    var email: String,

    @Column
    @NotEmpty(message = "Enter the user password!")
    @Size(max = 255)
    var password: String,

    @Column(name = "profile")
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Profile::class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_profile", joinColumns = [JoinColumn(name = "user_id")])
    var profiles: Set<Profile> = setOf(),

    @Column
    @Enumerated(EnumType.ORDINAL)
    var active: Status = Status.ACTIVE

)
