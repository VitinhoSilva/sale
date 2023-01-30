package com.jvprojetos17.sale.model

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
    @NotEmpty(message = "Informe o nome do usuário!")
    var name: String,

    @Column
    @CPF
    @NotEmpty(message = "Informe o cpf do usuário!")
    var cpf: String,

    @Column
    @Email
    @NotEmpty(message = "Informe o e-mail do usuário!")
    var email: String,

    @Column
    var active: Status = Status.ACTIVE

)
