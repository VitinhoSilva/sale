package com.jvprojetos17.sale.model

import com.jvprojetos17.sale.enums.Status
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity(name = "product")
data class Product (

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    @Size(max = 255)
    @NotEmpty(message = "Informe a descrição do produto!")
    var description: String,

    @Column
    @Size(max = 255)
    @NotEmpty(message = "Informe o código do produto!")
    var code: String,

    @Column
    @NotNull(message = "Informe o preço do produto!")
    var price: Double,

    @Column
    @Size(min = 1)
    @NotNull(message = "Informe a quantidade do estoque do produto!")
    var stock: Int,

    @Column
    var active: Status = Status.ACTIVE
)