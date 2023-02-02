package com.jvprojetos17.sale.model

import com.jvprojetos17.sale.enums.Status
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity(name = "product")
data class Product(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column
    @Size(max = 255)
    @NotEmpty(message = "Enter the product description!")
    var description: String,

    @Column
    @Size(max = 255)
    @NotEmpty(message = "Enter the product code!")
    var code: String,

    @Column
    @NotNull(message = "Inform the price of the product!")
    var price: Double,

    @Column
    @Size(min = 1)
    @NotNull(message = "Inform the quantity of the product's stock!")
    var stock: Int,

    @Column
    @Enumerated(EnumType.ORDINAL)
    var active: Status = Status.ACTIVE
)