package com.jvprojetos17.sale.model

import com.jvprojetos17.sale.enums.Profile
import com.jvprojetos17.sale.enums.Status
import org.hibernate.validator.constraints.br.CPF
import javax.persistence.*
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
    @CollectionTable(name = "user_profile", joinColumns = [JoinColumn(name = "user_id")])
    @ElementCollection(targetClass = Profile::class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    var profiles: Set<Profile> = setOf(),

    @Column
    @Enumerated(EnumType.ORDINAL)
    var active: Status = Status.ACTIVE

)
