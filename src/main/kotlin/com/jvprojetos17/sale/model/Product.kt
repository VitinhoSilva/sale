package com.jvprojetos17.sale.model

import com.jvprojetos17.sale.enums.Status
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity(name = "product")
data class Product(

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid", columnDefinition = "VARCHAR(36)")
    var uuid: String? = null,

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
    var active: Status = Status.TRUE,
)
